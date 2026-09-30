# Kubernetes deployment results

## Services

```bash
kubectl get svc
```

```text
NAME          TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)
backend       NodePort    10.101.210.176   <none>        4111:31541/TCP
frontend      NodePort    10.97.212.62     <none>        80:32475/TCP
kubernetes    ClusterIP   10.96.0.1        <none>        443/TCP
selenoid      NodePort    10.101.180.194   <none>        4444:31974/TCP
selenoid-ui   NodePort    10.99.5.240      <none>        8080:31162/TCP
```

Backend: `4111`
Frontend: `80`
Selenoid: `4444`
Selenoid UI: `8080`

## Pods

```bash
kubectl get pods
```

```text
NAME                          READY   STATUS
backend-7bc57d9d96-8dnks      1/1     Running
frontend-86cf74fdb-djz6r      1/1     Running
selenoid-7d4444fdff-tkcw2     1/1     Running
selenoid-ui-9c6cc6949-6w9f9   1/1     Running
```

All application pods are running.

## Logs

Backend:

```bash
kubectl logs deployment/backend
```

Backend started successfully on port `4111`.

Frontend:

```bash
kubectl logs deployment/frontend
```

Nginx started successfully.

Selenoid:

```bash
kubectl logs deployment/selenoid
```

Selenoid loaded `browsers.json` and started on port `4444`.

Selenoid UI:

```bash
kubectl logs deployment/selenoid-ui
```

Selenoid UI started on port `8080`.

## ConfigMap

```bash
kubectl get configmap selenoid-config -o yaml
```

The ConfigMap contains `browsers.json` with Chrome `128.0` configuration and is managed by Helm.

Secrets are not used.

## Port forwarding

```bash
kubectl port-forward svc/frontend 3000:80
kubectl port-forward svc/backend 4111:4111
kubectl port-forward svc/selenoid 4444:4444
kubectl port-forward svc/selenoid-ui 8080:8080
```

The services were checked through localhost:

```bash
curl -I http://localhost:3000
curl -I http://localhost:4111/actuator/health
curl -s http://localhost:4444/status
curl -I http://localhost:8080
```

All services returned a successful response.

Selenoid also reported Chrome `128.0` as available.

## Scaling

Frontend was scaled to 2 replicas:

```bash
kubectl scale deployment frontend --replicas=2
kubectl get pods
```

Two frontend pods were running.

Then it was scaled back to 1:

```bash
kubectl scale deployment frontend --replicas=1
kubectl get pods
```

One frontend pod remained running.

## Tests

After deployment:

```bash
mvn clean test
```

```text
Tests run: 34, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```
