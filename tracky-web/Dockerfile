## --- Multi-stage Build ---
#
## [1] JRE 빌드 전용 스테이지
#FROM openjdk:17 AS jre-builder
#RUN jlink --compress=2 --no-header-files --no-man-pages \
#          --strip-debug \
#          --add-modules java.base,java.logging,java.desktop \
#          --output /custom-jre
#
## [2] 빌드 스테이지 (Gradle 빌드)
#FROM gradle:8.7-jdk17 AS builder
#WORKDIR /app
#COPY . .
#RUN chmod +x ./gradlew
#RUN ./gradlew build
#
## [3] 실행 스테이지 (경량 JRE + 실행 환경)
#FROM debian:bullseye-slim
#WORKDIR /app
#
## jlink로 생성한 최소 JRE 복사
#COPY --from=jre-builder /custom-jre /custom-jre
#
## JAR 파일 복사
#COPY --from=builder /app/build/libs/*.jar app.jar
#
## 환경 변수 설정 (JRE 실행 가능하도록 설정)
#ENV JAVA_HOME=/custom-jre
#ENV PATH="$JAVA_HOME/bin:$PATH"
#
## 컨테이너에서 사용할 포트 설정
#EXPOSE 8080
#
## 애플리케이션 실행
#ENTRYPOINT ["java", "-jar", "app.jar"]


# OpenJDK 17을 베이스 이미지로 사용
FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/*.jar
COPY /${JAR_FILE} /app/app.jar

WORKDIR /app

# tzdata 패키지 설치 및 Asia/Seoul 시간대 설정
RUN apt-get update && apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo Asia/Seoul > /etc/timezone

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]
