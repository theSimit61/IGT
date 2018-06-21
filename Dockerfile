FROM openjdk:8
ADD target/IGTSpringboot.jar IGTSpringboot.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "IGTSpringboot.jar"]