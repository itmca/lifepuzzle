.PHONY: help setup start-rn-server start-rn-ios-dev start-rn-ios-prod start-rn-android-dev start-rn-android-prod start-backend start-infra health

help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

setup: ## Install dependencies for all apps (includes pod install)
	@echo "Setting up React Native app..."
	@cd apps/rn && npm install
	@if [ "$(shell uname)" = "Darwin" ]; then \
		echo "Running pod install for iOS..."; \
		cd apps/rn/ios && pod install; \
	fi
	@echo "Setting up Backend..."
	@cd apps/backend && ./gradlew clean build -x test

start-rn-server: ## Start React Native Metro Bundler
	@cd apps/rn && npm start

start-rn-ios-dev: ## Run iOS App (Dev Environment)
	@cd apps/rn && npm run ios:dev

start-rn-ios-prod: ## Run iOS App (Prod Environment)
	@cd apps/rn && npm run ios:prod

start-rn-android-dev: ## Run Android App (Dev Environment)
	@cd apps/rn && npm run android:dev

start-rn-android-prod: ## Run Android App (Prod Environment)
	@cd apps/rn && npm run android:prod

start-backend: ## Start Backend API (without full infra)
	@cd apps/backend && ./gradlew bootRun

start-infra: ## Start Backend Infrastructure (Docker)
	@cd infra/scripts && ./start-infra.sh

start-full-backend: ## Start Full Backend Service (Infra + API)
	@cd infra/scripts && ./start-full.sh

health: ## Check Health of Services
	@cd infra/scripts && ./health.sh
