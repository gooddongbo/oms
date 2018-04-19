FROM java:8
MAINTAINER dongbo
VOLUME /tmp
ADD test-1.0-SNAPSHOT.jar app.jar
RUN bash -c "touch /app.jar"
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "-cp" ,"/app.jar"]
