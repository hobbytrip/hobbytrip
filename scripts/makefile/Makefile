.PHONY: up down user-update community-update chat-update state-update sig-update notification-update

all: up

up:
	docker-compose pull
	docker-compose up -d zookeeper kafka discovery-service gateway-service user-service community-service chat-service sig-service state-service notification-service

down:
	docker-compose down

user-update:
	@echo "user temp start"
	docker-compose up -d user-service-temp
	docker-compose pull user-service
	@echo "sleep 10..."
	sleep 10
	@echo "user-service update start"
	docker-compose up -d user-service
	@echo "sleep 60..."
	sleep 60

	@echo "user temp remove"
	@echo "1. find container id"
	@container_id=$$(docker ps | grep user-service-temp | grep -v 'CONTAINER' | awk '{print $$1}'); \
	echo $$container_id; \
	echo "2. remove user temp"; \
	docker exec -i $$container_id kill -15 $$(docker exec -i $$container_id lsof -i | grep java | awk 'NR==1 {print $$2}'); \
	docker rm -f $$container_id; \
	docker image prune -f

community-update:
	@echo "community temp start"
	docker-compose up -d community-service-temp
	sleep 50
	docker-compose pull community-service
	@echo "sleep 10..."
	sleep 30
	@echo "community-service update start"
	docker-compose up -d community-service
	@echo "sleep 60..."
	sleep 60

	@echo "community temp remove"
	@echo "1. find container id"
	@container_id=$$(docker ps | grep community-service-temp | grep -v 'CONTAINER' | awk '{print $$1}'); \
	echo $$container_id; \
	echo "2. remove community temp"; \
	docker exec -i $$container_id kill -15 $$(docker exec -i $$container_id lsof -i | grep java | awk 'NR==1 {print $$2}'); \
	docker rm -f $$container_id; \
	docker image prune -f

chat-update:
	@echo "chat temp start"
	docker-compose up -d chat-service-temp
	docker-compose pull chat-service
	@echo "sleep 10..."
	sleep 10
	@echo "chat-service update start"
	docker-compose up -d chat-service
	@echo "sleep 60..."
	sleep 60

	@echo "chat temp remove"
	@echo "1. find container id"
	@container_id=$$(docker ps | grep chat-service-temp | grep -v 'CONTAINER' | awk '{print $$1}'); \
	echo $$container_id; \
	echo "2. remove chat temp"; \
	docker exec -i $$container_id kill -15 $$(docker exec -i $$container_id lsof -i | grep java | awk 'NR==1 {print $$2}'); \
	docker rm -f $$container_id; \
	docker image prune -f

state-update:
	@echo "state temp start"
	docker-compose up -d state-service-temp
	docker-compose pull state-service
	@echo "sleep 10..."
	sleep 10
	@echo "state-service update start"
	docker-compose up -d state-service
	@echo "sleep 60..."
	sleep 60

	@echo "state temp remove"
	@echo "1. find container id"
	@container_id=$$(docker ps | grep state-service-temp | grep -v 'CONTAINER' | awk '{print $$1}'); \
	echo $$container_id; \
	echo "2. remove state temp"; \
	docker exec -i $$container_id kill -15 $$(docker exec -i $$container_id lsof -i | grep java | awk 'NR==1 {print $$2}'); \
	docker rm -f $$container_id; \
	docker image prune -f

sig-update:
	@echo "sig temp start"
	docker-compose up -d sig-service-temp
	docker-compose pull sig-service
	@echo "sleep 10..."
	sleep 10
	@echo "sig-service update start"
	docker-compose up -d sig-service
	@echo "sleep 60..."
	sleep 60

	@echo "sig temp remove"
	@echo "1. find container id"
	@container_id=$$(docker ps | grep sig-service-temp | grep -v 'CONTAINER' | awk '{print $$1}'); \
	echo $$container_id; \
	echo "2. remove sig temp"; \
	docker exec -i $$container_id kill -15 $$(docker exec -i $$container_id lsof -i | grep java | awk 'NR==1 {print $$2}'); \
	docker rm -f $$container_id; \
	docker image prune -f

notification-update:
	@echo "notification temp start"
	docker-compose up -d notification-service-temp
	docker-compose pull notification-service
	@echo "sleep 10..."
	sleep 10
	@echo "notification-service update start"
	docker-compose up -d notification-service
	@echo "sleep 60..."
	sleep 60

	@echo "notification temp remove"
	@echo "1. find container id"
	@container_id=$$(docker ps | grep notification-service-temp | grep -v 'CONTAINER' | awk '{print $$1}'); \
	echo $$container_id; \
	echo "2. remove notification temp"; \
	docker exec -i $$container_id kill -15 $$(docker exec -i $$container_id lsof -i | grep java | awk 'NR==1 {print $$2}'); \
	docker rm -f $$container_id; \
	docker image prune -f
