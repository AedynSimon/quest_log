# Build:
#   docker build -t questlog .
# Run:
#   docker run -p 8080:8080 questlog
#
# Uses a multi-stage build so the final image is small-ish.

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/questlog-1.0.0.jar /app/app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-lc","java $JAVA_OPTS -jar /app/app.jar"]
