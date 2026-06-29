FROM java

#ENV JAVA_OPTS="\
#-Dspring.cloud.nacos.config.namespace=d3bde2d0-f32b-447d-ae2d-c3f3658514ae \
#-Dspring.cloud.nacos.discovery.namespace=d3bde2d0-f32b-447d-ae2d-c3f3658514ae"

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD smccloud-ops-product-exec.jar app.jar

ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8 -Dspring.profiles.active=${PROFILE} -jar /opt/app.jar
ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar app.jar "]