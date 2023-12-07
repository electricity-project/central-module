FROM eclipse-temurin:21-jre-jammy

COPY service-app-0.0.1.jar /central-module.jar
CMD ["java", "-jar", "/central-module.jar"]
