FROM amd64/amazoncorretto:17

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

RUN yum install -y procps lsof

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar",  "-Dspring.profiles.active=dev", "/app.jar"]
