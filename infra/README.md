# Infrastructure Configuration

LifePuzzle 로컬 개발 환경을 위한 인프라 설정

## Structure

```bash
infra/
├── docker/                       # Docker Compose setup
│   ├── docker-compose.yml        # 인프라만 (MySQL + RabbitMQ)
│   ├── docker-compose.full.yml   # 전체 스택 (앱 포함)
│   ├── mysql/
│   │   └── init/
│   │       └── 01-init.sql       # MySQL 초기화 스크립트
│   └── rabbitmq/
│       ├── rabbitmq.conf         # RabbitMQ 설정
│       └── definitions.json      # Queue/Exchange 정의
├── scripts/                      # 개발 유틸리티 스크립트
└── README.md
```

> **Note**: K8s 운영 환경 설정은 [homelab](https://github.com/itmca/homelab) 레포에서 관리합니다.

## Docker Compose

### Option 1: Infrastructure Only (백엔드 개발용)

IDE에서 앱을 직접 실행할 때 사용:

```bash
cd infra/docker
docker-compose up -d
```

**Services:**
- **MySQL**: `localhost:3306`
- **RabbitMQ**:
  - AMQP: `localhost:5672`
  - Management UI: `http://localhost:15672`

### Option 2: Full Stack (프론트엔드 개발용)

모든 서비스를 Docker로 실행:

```bash
cd infra/docker

# 환경변수 설정
cp .env.example .env
# .env 파일에서 AWS 자격증명 설정

# 전체 스택 시작
docker-compose -f docker-compose.full.yml up -d
```

**Services:**
- **MySQL**: `localhost:3306`
- **RabbitMQ**: `localhost:5672`, UI: `http://localhost:15672`
- **LifePuzzle API**: `http://localhost:8080`
- **Image Resizer**: `http://localhost:9000`

## Scripts

### 프론트엔드 개발자
```bash
./tools/scripts/setup-dev.sh     # 최초 설정
./tools/scripts/start-full.sh    # 전체 서비스 시작
./tools/scripts/health.sh        # 상태 확인
./tools/scripts/logs.sh api      # 로그 확인
./tools/scripts/stop.sh          # 종료
```

### 백엔드 개발자
```bash
./tools/scripts/start-infra.sh   # MySQL + RabbitMQ만 시작
# IDE에서 앱 실행
./tools/scripts/stop.sh          # 종료
```

## Default Credentials

| Service | User | Password | Note |
|---------|------|----------|------|
| MySQL (root) | root | rootpassword | 관리용 |
| MySQL | lifepuzzle | lifepuzzlepass | 앱 사용 |
| RabbitMQ | lifepuzzle | lifepuzzlepass | vhost: lifepuzzle |

## Environment Variables

앱에서 사용하는 환경변수:

```bash
# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=lifepuzzle
DB_USER=lifepuzzle
DB_PASSWORD=lifepuzzlepass

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=lifepuzzle
RABBITMQ_PASSWORD=lifepuzzlepass
RABBITMQ_VHOST=lifepuzzle
```

## Production Deployment

운영 환경 배포는 [homelab](https://github.com/itmca/homelab) 레포에서 관리합니다:

- Nginx 리버스 프록시
- K8s Helm 배포 설정
- DB 백업 스크립트
- SSL 인증서 관리
