#!/bin/bash

set -e

cd "$(dirname "$0")/infra/docker_compose"

cleanup() {
    echo ">>> Остановка Docker Compose"
    docker compose down
}

trap cleanup EXIT

echo ">>> Запуск Docker Compose"
docker compose up -d

echo ">>> Запуск API-тестов"

docker run --rm \
    --network nbank-network \
    -e APIBASEURL=http://backend:4111 \
    alexsnp1/nbank-tests:latest

echo ">>> Запуск UI-тестов"

docker run --rm \
    --network nbank-network \
    -e TEST_PROFILE=ui \
    -e APIBASEURL=http://backend:4111 \
    -e UIBASEURL=http://nginx \
    -e UIREMOTE=http://selenoid:4444/wd/hub \
    alexsnp1/nbank-tests:latest