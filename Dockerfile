# our base image
FROM jetty
COPY ./Service/build/libs/service-1.0.1-SNAPSHOT.war /var/lib/jetty/webapps/