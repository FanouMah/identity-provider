FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /identity-provider
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn spring-boot:run

FROM tomcat:10.1-jdk17
COPY --from=build /identity-provider/target/*.war /usr/local/tomcat/webapps/identity-provider.war
CMD ["catalina.sh", "run"]