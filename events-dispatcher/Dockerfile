FROM openjdk:8-jre-alpine

ENV APP_FILE app.jar
ENV IMAGE_HOME /opt/app-folder

COPY target/*-fat.jar $IMAGE_HOME/$APP_FILE

WORKDIR $IMAGE_HOME

ENTRYPOINT java -jar  -Dhost=10.5.12.243 -Dport=32231 ${APP_FILE} -cluster
