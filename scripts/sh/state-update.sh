#!/bin/bash

echo "state-service-temp start"
docker-compose up -d state-service-temp

echo "sleep 30..."
sleep 30

docker-compose pull state-service
echo "sleep 15..."
sleep 15

echo "state-service remove"
echo "1. find container id"
container_id=$(docker ps | grep state-service | grep -v 'CONTAINER' | awk 'NR==2 {print $1}')
echo $container_id
echo "2. remove state-service"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id

echo "state-service update start"
docker-compose up -d state-service
echo "sleep 60..."
sleep 60

echo "state-service-temp remove"
echo "1. find container id"
container_id=$(docker ps | grep state-service-temp | grep -v 'CONTAINER' | awk '{print $1}')
echo $container_id
echo "2. remove state-service-temp"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id
docker image prune -f
