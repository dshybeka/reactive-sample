# Simple chat application

The idea is to see how eventBus is share among multiple pods and js socket

## Steps for running in GCP (assuming project with billing info configured)

1. Create k8s cluster

2. connect to the cluster -> run in cloud shell. Or manually run in gcp shell: gcloud container clusters get-credentials <cluster-name>  --zone <cluster-zone> 

3. git clone https://github.com/dshybeka/reactive-sample.git
Build project
3.1 cd reactive-sample
3.2 mvn package
3.3 cd vertx-cluster
Build docker image 
3.4 docker build -t vertx-cluster .
Tag image and push to gcp registry
3.5 docker tag vertx-cluster gcr.io/rise-164421/vertx-cluster
3.6 docker push gcr.io/rise-164421/vertx-cluster 

Create deployment
4. kubectl create -f k8s/vertx-cluster-deployment.yaml

Create service
5. kubectl create -f k8s/vertx-cluster-service.yaml

Configure rbac roles for hazelcast
6. kubectl create -f k8s/rbac.yaml

Scale pods to receive traffic
6. kubectl scale deployment vertx-cluster-deployment --replicas 3

7. open app

8. clean up by removing pods, service, deployment and cluster itself:
- kubectl delete deployments ${name}
- kubectl delete services ${name}




