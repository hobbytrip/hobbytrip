#!/bin/bash

echo "community temp start"
docker-compose up -d community-service-temp
sleep 30
docker-compose pull community-service
echo "sleep 30..."
sleep 30

echo "community remove"
echo "1. find container id"
container_id=$(docker ps | grep community-service | grep -v 'CONTAINER' | awk 'NR==2 {print $1}')
echo $container_id
echo "2. remove community"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id

echo "community-service update start"
docker-compose up -d community-service
echo "sleep 60..."
sleep 60

echo "community temp remove"
echo "1. find container id"
container_id=$(docker ps | grep community-service-temp | grep -v 'CONTAINER' | awk '{print $1}')
echo $container_id
echo "2. remove community temp"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id
docker image prune -f
