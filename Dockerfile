FROM openjdk:11-jdk-slim
COPY target/WorldOfManga-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java","-jar","WorldOfManga-0.0.1-SNAPSHOT.jar"]