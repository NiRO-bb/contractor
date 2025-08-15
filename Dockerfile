FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./target/*.jar contractor.jar

ENTRYPOINT ["java", "-jar", "contractor.jar"]