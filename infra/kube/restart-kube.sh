#!/bin/bash

# Запустили локальный Kubernetes-кластер с помощью minikube, используя Docker как драйвер
# (кластер будет запущен внутри докер контейнера)
minikube start --driver=docker --container-runtime=docker

kubectl delete configmap selenoid-config --ignore-not-found
# Устанавливаем Helm чарт с именем релиза nbank, беря шаблоны из ./nbank-chart
# Это создаст все ресурсы, описанные в шаблонах Helm (Deployment, Service, ConfigMap)
helm upgrade --install nbank ./nbank-chart

# Ждём запуска Deployment'ов
kubectl rollout status deployment/backend
kubectl rollout status deployment/frontend
kubectl rollout status deployment/selenoid
kubectl rollout status deployment/selenoid-ui

# Все сервисы в namespace=default
kubectl get svc

# Все поды в namespace=default
kubectl get pods

# Логи сервисов
kubectl logs deployment/backend
kubectl logs deployment/frontend
kubectl logs deployment/selenoid
kubectl logs deployment/selenoid-ui

# Останавливаем старые port-forward
pkill -f "kubectl port-forward svc/frontend 3000:80" || true
pkill -f "kubectl port-forward svc/backend 4111:4111" || true
pkill -f "kubectl port-forward svc/selenoid 4444:4444" || true
pkill -f "kubectl port-forward svc/selenoid-ui 8080:8080" || true

# Проброс портов на локальную машину
# чтобы пробросить порт в фоновом режиме надо добавить к данной команде > /dev/null 2>&1 &
kubectl port-forward svc/frontend 3000:80 > /dev/null 2>&1 &
kubectl port-forward svc/backend 4111:4111 > /dev/null 2>&1 &
kubectl port-forward svc/selenoid 4444:4444 > /dev/null 2>&1 &
kubectl port-forward svc/selenoid-ui 8080:8080 > /dev/null 2>&1 &