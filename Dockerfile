# 1. Java 17 이미지 사용
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle wrapper와 프로젝트 전체 복사
COPY . .

# 4. gradle 빌드 (실행 권한 필요)
RUN chmod +x ./gradlew
RUN ./gradlew build

# 5. 실행
CMD ["java", "-jar", "build/libs/birthday-0.0.1-SNAPSHOT.jar"]
