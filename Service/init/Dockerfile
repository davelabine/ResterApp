FROM alpine:3.7

RUN apk add --no-cache curl

ADD ./init.sh /

CMD ["/bin/sh", "init.sh"]
