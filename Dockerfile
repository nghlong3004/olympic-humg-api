FROM eclipse-temurin:21-jre

LABEL maintainer="nhuyhoang1305@gmail.com"

ENV app_name olympic-humg-api

RUN mkdir -p /opt/$app_name/
RUN mkdir -p /var/log/$app_name

ADD ./output/*.jar /opt/$app_name/$app_name.jar
