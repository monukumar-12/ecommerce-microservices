FROM discovery-service:latest

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jgitar","app.jar"]