FROM discovery-service:latest

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 9020
ENTRYPOINT ["java","-jar","app.jar"]