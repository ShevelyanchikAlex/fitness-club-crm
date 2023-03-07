FROM adoptopenjdk/openjdk15:alpine-jre

ARG APP_NAME="user-service"
ARG JAR_FILE="/build/libs/${APP_NAME}.jar"

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]