FROM gradle:latest as build
LABEL authors="luizcosta"

ADD . /app
WORKDIR /app
RUN gradle clean build

FROM amazoncorretto:21-alpine as corretto-jdk

RUN apk add --no-cache binutils

RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=zip-6 \
         --output /customjre

FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=corretto-jdk /customjre $JAVA_HOME
COPY --from=0 /app/addressservice/build/libs/addressservice-all.jar /addressservice.jar

ENTRYPOINT ["/jre/bin/java", "-jar", "addressservice.jar"]
#ENTRYPOINT ["tail", "-f", "/dev/null"]

