FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY settings.xml /usr/share/maven/conf/settings.xml
RUN mvn clean package -DskipTests -X


FROM openjdk:11-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
