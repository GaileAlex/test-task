FROM gradle:8.6-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build --rerun-tasks --no-build-cache --no-daemon -x test

FROM gradle:8.6-jdk17

EXPOSE 8095

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar tuum.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","tuum.jar","--host","0.0.0.0","--port","8095"]
