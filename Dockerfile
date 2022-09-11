FROM maven:3.8.5-openjdk-17 as builder

WORKDIR /build

COPY .mvn/ .mvn
COPY pom.xml ./

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean install

FROM openjdk:17-alpine as runner

WORKDIR /app

COPY --from=builder /build/target/*.jar ./*.jar

ENTRYPOINT ["java", "-jar", "./*.jar" ]