#!/bin/bash

cd "$(dirname "$0")"

echo ">>> Остановить Docker Compose"
docker compose down

echo ">>> Docker pull все образы браузеров"
docker pull selenoid/vnc_chrome:128.0

echo ">>> Запуск Docker Compose"
docker compose up -d