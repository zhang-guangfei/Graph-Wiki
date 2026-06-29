FROM java

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD smccloud-ops-shareapp-exec.jar app.jar

ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar app.jar "]