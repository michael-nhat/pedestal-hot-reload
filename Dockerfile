FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/my-pe-reload-service-0.0.1-SNAPSHOT-standalone.jar /my-pe-reload-service/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/my-pe-reload-service/app.jar"]
