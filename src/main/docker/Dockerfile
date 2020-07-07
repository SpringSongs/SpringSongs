FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD cn.chenyongcan.admin-0.0.1-SNAPSHOT.jar index-web.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/index-web.jar"]