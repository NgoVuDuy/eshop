# Base image JDK 17 nhẹ
FROM openjdk:17-jdk-slim

# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar vào container
COPY target/electroshop-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (Spring Boot mặc định)
EXPOSE 8080

# Lệnh chạy app
ENTRYPOINT ["java","-jar","app.jar"]