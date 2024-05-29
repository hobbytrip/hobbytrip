FROM amd64/amazoncorretto:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul",  "-Dspring.profiles.active=dev","-jar", "/app.jar"]