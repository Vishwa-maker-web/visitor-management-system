FROM eclipse-temurin:21-jdk
WORKDIR /app
RUN mkdir -p /app/qr
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
EXPOSE 4040
CMD ["java", "-jar", "target/visitor-0.0.1-SNAPSHOT.jar"]
