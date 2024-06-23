#!/bin/bash

echo "user-service-temp start"
docker-compose up -d --no-build user-service-temp

echo "sleep 30..."
sleep 30
docker-compose pull user-service
echo "sleep 15..."
sleep 15

echo "user-service remove"
echo "1. find container id"
container_id=$(docker ps | grep user-service | grep -v 'CONTAINER' | awk 'NR==2 {print $1}')
echo $container_id
echo "2. remove user-service"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id

echo "user-service update start"
docker-compose up -d user-service
echo "sleep 60..."
sleep 60

echo "user-service-temp remove"
echo "1. find container id"
container_id=$(docker ps | grep user-service-temp | grep -v 'CONTAINER' | awk '{print $1}')
echo $container_id
echo "2. remove user-service-temp"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id
docker image prune -f
