FROM progrium/busybox

RUN opkg-install nc curl bash

ADD ./init.sh /

CMD ["/bin/bash", "-c", "init.sh"]
