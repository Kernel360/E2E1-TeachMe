FROM amazoncorretto:11
COPY build/libs/teachme-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]