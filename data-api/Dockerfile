FROM amazoncorretto:21-alpine AS base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN ./mvnw dependency:resolve

COPY src ./src

RUN ./mvnw package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=base /app/target/*.jar ./app.jar

RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup && \
    chown -R appuser:appgroup /app

USER appuser

CMD ["java", "-jar", "app.jar"]
