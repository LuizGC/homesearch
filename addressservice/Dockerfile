FROM gradle:latest
LABEL authors="luizcosta"

ADD . /app
WORKDIR /app
RUN gradle clean build

FROM 21-alpine3.20
COPY --from=0 /app/build/libs/app-all.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
#ENTRYPOINT ["tail", "-f", "/dev/null"]

