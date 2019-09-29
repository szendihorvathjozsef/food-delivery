FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/backend.jar backend.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/backend.jar"]