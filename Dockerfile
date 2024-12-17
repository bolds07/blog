FROM amazoncorretto:15-alpine-jdk

COPY application/target/java-blog-application-*.jar app.jar
COPY pom.xml .

RUN mkdir logs
RUN mkdir resources

CMD java -jar app.jar
