FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/local/autocomplete
VOLUME /usr/local/autocomplete
ADD target/autocomplete-1.0.1.jar autocomplete.jar
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","autocomplete.jar"]

