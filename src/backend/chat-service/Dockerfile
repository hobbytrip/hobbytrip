#FROM openjdk:17-ea-17-jdk
#VOLUME /tmp
#COPY ./build/libs/chatservice-0.0.1-SNAPSHOT.jar chat-service.jar
#ENTRYPOINT ["java", "-jar", "-Dserver.port=7070", "chat-service.jar"]

FROM amd64/amazoncorretto:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar",  "-Dspring.profiles.active=test", "/app.jar"]