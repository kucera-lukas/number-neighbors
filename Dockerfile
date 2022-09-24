FROM maven:3.8.5-openjdk-17 as builder

WORKDIR /build

COPY .mvn/ .mvn
COPY pom.xml ./

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean package -DskipTests=true

FROM openjdk:17 as runner

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar" ]
