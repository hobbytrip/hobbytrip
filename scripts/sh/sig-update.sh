#!/bin/bash

echo "sig-service-temp start"
docker-compose up -d --no-build sig-service-temp
echo "sleep 30..."
sleep 30

docker-compose pull sig-service
echo "sleep 15..."
sleep 15

echo "sig-service remove"
echo "1. find container id"
container_id=$(docker ps | grep sig-service | grep -v 'CONTAINER' | awk 'NR==2 {print $1}')
echo $container_id
echo "2. remove sig"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id

echo "sig-service update start"
docker-compose up -d sig-service
echo "sleep 60..."
sleep 60

echo "sig-service-temp remove"
echo "1. find container id"
container_id=$(docker ps | grep sig-service-temp | grep -v 'CONTAINER' | awk '{print $1}')
echo $container_id
echo "2. remove sig-service-temp"
docker exec -i $container_id kill -15 $(docker exec -i $container_id lsof -i | grep java | awk 'NR==1 {print $2}')
docker rm -f $container_id
docker image prune -f
