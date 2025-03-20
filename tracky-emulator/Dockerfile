# --- Multi-stage Build ---

# 빌드 단계 (Build Stage)
FROM gradle:8.7-jdk17 AS builder

# 작업 디렉토리 설정.  /app으로 하는 것이 일반적입니다.
WORKDIR /app

# 소스 코드를 컨테이너 안으로 복사.
COPY . .

# Gradle을 사용하여 애플리케이션 빌드.
# gradlew 파일에 실행 권한이 없는 경우, `RUN chmod +x ./gradlew` 추가
RUN chmod +x ./gradlew
RUN ./gradlew build

# 실행 단계 (Run Stage)
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 실행 단계로 복사
# (경로는 프로젝트 구조에 따라 조정해야 할 수 있습니다)
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]