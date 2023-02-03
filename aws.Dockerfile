#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/world-of-manga/src
COPY pom.xml /home/world-of-manga
RUN mvn -f /home/world-of-manga/pom.xml clean package -Dmaven.test.skip=true
#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/world-of-manga/target/WorldOfManga-0.0.1-SNAPSHOT.jar /usr/local/lib/WorldOfManga-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/WorldOfManga-0.0.1-SNAPSHOT.jar"]