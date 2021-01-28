# Kubernetes

Neste repositório estarão disponíveis nosso *Workshop* de implementação fazendo uso da tecnologia [Kubernetes](https://kubernetes.io/) & [Minikube](https://minikube.sigs.k8s.io/docs/start/)

## Pré Requisitos

- [Minikube](https://minikube.sigs.k8s.io/docs/start/)

## Workshop

0. [Minikube Start](#workshop-minikube-start)
1. [Minikube DashBoard](#workshop-minikube-dashboard)
2. [Criação de Namespace](#workshop-k8s-namespace)
3. [Deploy EchoServer](#workshop-k8s-echoserver)
4. [EchoServer Service](#workshop-k8s-echoserver-service)
5. [EchoServer Shell Pod](#workshop-k8s-echoserver-shellpod)
6. [EchoServer Scale](#workshop-k8s-echoserver-scale)

## Implementação

### 0 - Minikube Start <a name="workshop-minikube-start">

  Sintaxe:
  ```
  minikube start --memory=8192 --cpus=3`
  minikube status

  minikube
  type: Control Plane
  host: Running
  kubelet: Running
  apiserver: Running
  kubeconfig: Configured
  ```

### 1 - Minikube DashBoard <a name="workshop-minikube-dashboard">

  Sintaxe: `minikube dashboard`

### 2 - Criação de Namespace <a name="workshop-k8s-namespace">

  Sintaxe: `kubectl create namespace impacta`
  ```
  namespace/impacta created
  ```

### 3 - Deploy EchoServer <a name="workshop-k8s-echoserver">

  Sintaxe: `kubectl -n impacta create deployment hello-node --image=k8s.gcr.io/echoserver:1.4`
  ```
  deployment.apps/hello-node created

  kubectl -n get pods
  NAME                          READY   STATUS    RESTARTS   AGE
  hello-node-7bf657c596-k888h   1/1     Running   0          4m35s

  kubectl -n get deployments
  NAME         READY   UP-TO-DATE   AVAILABLE   AGE
  hello-node   1/1     1            1           4m47s
  ```

### 4 - EchoServer Service <a name="workshop-k8s-echoserver-service">

  Sintaxe: `kubectl -n impacta expose deployment hello-node --type=LoadBalancer --port=8080`
  ```
  service/hello-node exposed

  kubectl -n impacta get service
  NAME         TYPE           CLUSTER-IP    EXTERNAL-IP   PORT(S)          AGE
  hello-node   LoadBalancer   10.110.2.90   <pending>     8080:32655/TCP   39s

  minikube -n impacta service hello-node
  |-----------|------------|-------------|---------------------------|
  | NAMESPACE |    NAME    | TARGET PORT |            URL            |
  |-----------|------------|-------------|---------------------------|
  | impacta   | hello-node |        8080 | http://192.168.64.4:32655 |
  ```

### 5 - EchoServer Shell Pod <a name="workshop-k8s-echoserver-shellpod">

  Sintaxe:
  ```
  kubectl -n impacta get pods
  NAME                          READY   STATUS    RESTARTS   AGE
  hello-node-7bf657c596-k888h   1/1     Running   0          19m

  kubectl -n impacta exec -it $POD_ID -- /bin/bash
  root@hello-node-7bf657c596-k888h:/#
  ```

### 6 - EchoServer Scale Pod <a name="workshop-k8s-echoserver-scale">

  Sintaxe:
  ```
  kubectl -n impacta get deployments
  NAME         READY   UP-TO-DATE   AVAILABLE   AGE
  hello-node   1/1     1            1           23m

  kubectl -n impacta scale --replicas=3 deployment hello-node
  deployment.apps/hello-node scaled

  kubectl -n impacta get pods
  NAME                          READY   STATUS    RESTARTS   AGE
  hello-node-7bf657c596-7mjw9   1/1     Running   0          20s
  hello-node-7bf657c596-k888h   1/1     Running   0          24m
  hello-node-7bf657c596-kcq8q   1/1     Running   0          20s

  kubectl -n impacta get deployments hello-node --show-label
  NAME         READY   UP-TO-DATE   AVAILABLE   AGE   LABELS
  hello-node   3/3     3            3           37m   app=hello-node
  kubectl -n impacta delete all -l app=hello-node
  pod "hello-node-7bf657c596-7mjw9" deleted
  pod "hello-node-7bf657c596-k888h" deleted
  pod "hello-node-7bf657c596-kcq8q" deleted
  service "hello-node" deleted
  deployment.apps "hello-node" deleted
  replicaset.apps "hello-node-7bf657c596" deleted
  ```
