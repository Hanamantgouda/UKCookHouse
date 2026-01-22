# -------- Build Stage --------
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

# -------- Runtime Stage --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/UKCookHouse-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
