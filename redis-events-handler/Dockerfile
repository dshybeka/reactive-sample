FROM openjdk:8-jre-alpine

ENV APP_FILE app.jar
ENV IMAGE_HOME /opt/app-folder

COPY target/*-fat.jar $IMAGE_HOME/$APP_FILE

WORKDIR $IMAGE_HOME

EXPOSE 7070

ENTRYPOINT java -jar ${APP_FILE} -cluster
