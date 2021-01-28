FROM openjdk:11-jdk-slim
ADD target/WorldOfManga-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","WorldOfManga-0.0.1-SNAPSHOT.jar"]