# FROM maven:3.9-eclipse-temurin-17 AS build
# WORKDIR /identity-provider
# COPY ./pom.xml ./pom.xml
# COPY ./src ./src
# RUN mvn clean package -DskipTests

# FROM tomcat:10.1-jdk17
# COPY --from=build /identity-provider/target/*.war /usr/local/tomcat/webapps/identity-provider.war
# CMD ["catalina.sh", "run"]

FROM openjdk:17-oracle
COPY target/*.jar identity-provider.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","identity-provider.jar" ]