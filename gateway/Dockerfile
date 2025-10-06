FROM bellsoft/liberica-openjdk-alpine:21.0.3

WORKDIR /app

COPY target/mcsv-gateway.jar /app/mcsv-gateway.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "mcsv-gateway.jar"]
