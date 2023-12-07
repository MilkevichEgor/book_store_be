FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /home/app
COPY . .
RUN ./mvnw install -DskipTests
#RUN ./mvnw clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /home/app/target /app/target
EXPOSE 8080
CMD ["java", "-jar", "/app/target/book-store-be-0.0.1-SNAPSHOT.jar"]