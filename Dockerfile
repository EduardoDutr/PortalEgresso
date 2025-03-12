FROM maven:3.9.9-amazoncorretto-23 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:23-jdk

WORKDIR /app

COPY --from=build /app/target/portal-egressos-1.0.jar /app

EXPOSE 8080

CMD ["java", "-jar", "portal-egressos-1.0.jar"]