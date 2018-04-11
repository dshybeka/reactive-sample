# reactive-sample
Vertx and spring5 webflux (annotations and functional based approaches).
+ Gatling load tests.

## Applications are pretty simple: 
1. get json input
2. convert to model
3. convert back to json
4. return

## Notes
 - mvn clean package - to create fat jars
 - all applications are run on 8080 port
 - Gatling test can be run from IDE(mvn package first) or via gatling.sh

## Cluster
1. Install minikube(need to link kubectl and docker with docker env). Start on windows 10: minikube --vm-driver hyperv --hyperv-virtual-switch primary_virtual_switch start
2. Create docker image for vertx cluster project
3. kubectl run vertx-cluster --image=vertx-cluster --port=7070 --image-pull-policy=IfNotPresent - create docker deployment
4. kubectl expose deployment vertx-cluster --type="LoadBalancer" - expose docker service
5. minikube service vertx-cluster --url - to get service url
6. kubectl set image deployment/vertx-cluster vertx-cluster=vertx-cluster:v3 - update image
7. open dashboard and scale
