FROM ubuntu:22.04
LABEL authors="mykaneki"

RUN sed -i 's/archive.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list
RUN apt-get update
RUN apt-get install -y openjdk-17-jre-headless
RUN apt-get install
ADD generatenotify-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar"]
