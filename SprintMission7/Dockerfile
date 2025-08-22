ARG BUILDER_IMAGE=gradle:7.6.0-jdk17
ARG RUNTIME_IMAGE=amazoncorretto:17

FROM ${BUILDER_IMAGE} AS builder

USER root
WORKDIR /app

ENV GRADLE_USER_HOME=/home/gradle/.gradle
RUN mkdir -p $GRADLE_USER_HOME && chown -R gradle:gradle /home/gradle /app
USER gradle

COPY --chown=gradle:gradle gradlew ./gradlew
COPY --chown=gradle:gradle gradle ./gradle
COPY --chown=gradle:gradle build.gradle settings.gradle ./
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon dependencies || true

COPY --chown=gradle:gradle src ./src
RUN ./gradlew clean build --no-daemon -x test


FROM ${RUNTIME_IMAGE}
WORKDIR /app

ENV PROJECT_NAME=discodeit \
    PROJECT_VERSION=0.0.1-SNAPSHOT \
    JVM_OPTS=""

COPY --from=builder /app/build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar app.jar

EXPOSE 80

ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar app.jar"]
