IMAGE_NAME=nbank-tests
DOCKERHUB_USERNAME=alexsnp1
TAG=latest
echo ">>> Авторизация в Docker Hub"

echo "$DOCKERHUB_TOKEN" | docker login \
  -u "$DOCKERHUB_USERNAME" \
  --password-stdin

echo ">>> Тегирование образа"

docker tag "$IMAGE_NAME:latest" "${DOCKERHUB_USERNAME}/${IMAGE_NAME}:$TAG"

echo ">>> Push образа"
docker push "$DOCKERHUB_USERNAME/$IMAGE_NAME:$TAG"

echo ">>> Образ опубликован"
echo "Скачать его можно командой:"
echo "docker pull $DOCKERHUB_USERNAME/$IMAGE_NAME:$TAG"