From openjdk:8-jre-alpine

RUN apk add graphicsmagick=1.3.30-r0
RUN ln -s /usr/bin/gm /usr/local/bin/gm

ADD app.jar /opt/taskagile/app.jar
ADD application-docker.properties /config/application-docker.properties

EXPOSE 8080 9000

ENTRYPOINT [ "java", "-jar", "/opt/taskagile/app.jar" ]
