import json
import logging
import time
from typing import Callable

import pika
from pika.adapters.blocking_connection import BlockingChannel
from pika.spec import Basic, BasicProperties

from config import Config

logger = logging.getLogger(__name__)


class RabbitMQConsumer:
    def __init__(self, message_handler: Callable[[dict], bool]):
        self.message_handler = message_handler
        self.connection = None
        self.channel = None

    def connect(self) -> bool:
        """Establish connection to RabbitMQ."""
        try:
            credentials = pika.PlainCredentials(
                Config.RABBITMQ_USERNAME, Config.RABBITMQ_PASSWORD
            )
            parameters = pika.ConnectionParameters(
                host=Config.RABBITMQ_HOST,
                port=Config.RABBITMQ_PORT,
                virtual_host=Config.RABBITMQ_VHOST,
                credentials=credentials,
                heartbeat=600,
                blocked_connection_timeout=300,
            )

            self.connection = pika.BlockingConnection(parameters)
            self.channel = self.connection.channel()

            # Set prefetch count to 1 (process one message at a time)
            self.channel.basic_qos(prefetch_count=1)

            logger.info(
                f"Connected to RabbitMQ: {Config.RABBITMQ_HOST}:{Config.RABBITMQ_PORT}"
            )
            return True

        except Exception as e:
            logger.error(f"Failed to connect to RabbitMQ: {e}")
            return False

    def start_consuming(self):
        """Start consuming messages from the queue."""
        if not self.channel:
            raise RuntimeError("Not connected to RabbitMQ")

        queue_name = Config.QUEUE_NAME
        logger.info(f"Starting to consume from queue: {queue_name}")

        # Declare queue (in case it doesn't exist)
        # This should match the Spring Cloud Stream configuration
        self.channel.queue_declare(
            queue=queue_name,
            durable=True,
            arguments={
                "x-dead-letter-exchange": "DLX",
                "x-dead-letter-routing-key": queue_name,
            },
        )

        self.channel.basic_consume(
            queue=queue_name,
            on_message_callback=self._on_message,
            auto_ack=False,
        )

        logger.info("Waiting for messages...")
        self.channel.start_consuming()

    def _on_message(
        self,
        channel: BlockingChannel,
        method: Basic.Deliver,
        properties: BasicProperties,
        body: bytes,
    ):
        """Handle incoming message."""
        try:
            message = body.decode("utf-8")
            logger.info(f"Received message: {message}")

            # Parse JSON
            data = json.loads(message)

            # Process message
            success = self.message_handler(data)

            if success:
                # Acknowledge message
                channel.basic_ack(delivery_tag=method.delivery_tag)
                logger.info("Message processed and acknowledged")
            else:
                # Reject and requeue (will go to DLQ after max retries)
                channel.basic_nack(delivery_tag=method.delivery_tag, requeue=False)
                logger.warning("Message rejected")

        except json.JSONDecodeError as e:
            logger.error(f"Failed to parse message as JSON: {e}")
            channel.basic_nack(delivery_tag=method.delivery_tag, requeue=False)

        except Exception as e:
            logger.error(f"Error processing message: {e}", exc_info=True)
            channel.basic_nack(delivery_tag=method.delivery_tag, requeue=False)

    def close(self):
        """Close the connection."""
        if self.connection and self.connection.is_open:
            self.connection.close()
            logger.info("RabbitMQ connection closed")

    def run_with_reconnect(self, max_retries: int = -1):
        """Run consumer with automatic reconnection."""
        retry_count = 0
        retry_delay = 5

        while max_retries < 0 or retry_count < max_retries:
            try:
                if self.connect():
                    retry_count = 0  # Reset on successful connection
                    self.start_consuming()
                else:
                    raise RuntimeError("Connection failed")

            except pika.exceptions.AMQPConnectionError as e:
                logger.error(f"Connection error: {e}")

            except KeyboardInterrupt:
                logger.info("Shutdown requested")
                break

            except Exception as e:
                logger.error(f"Unexpected error: {e}", exc_info=True)

            finally:
                self.close()

            retry_count += 1
            logger.info(f"Reconnecting in {retry_delay} seconds... (attempt {retry_count})")
            time.sleep(retry_delay)

        logger.info("Consumer stopped")
