# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]