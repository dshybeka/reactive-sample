mvn package

from vertx-cluster: docker build -t vertx-cluster .
docker tag vertx-cluster gcr.io/rise-164421/vertx-cluster
docker push gcr.io/rise-164421/vertx-cluster

create cluster

gcloud container clusters get-credentials <cluster-name>  --zone <cluster-zone>

kubectl create -f deployment.yaml

kubectl create -f service.yaml

kubectl scale deployment vertx-cluster-deployment --replicas 0


