FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY src/main/java/com/example/visitor .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
EXPOSE 4040
CMD ["java", "-jar", "target/visitor-0.0.1-SNAPSHOT.jar"]