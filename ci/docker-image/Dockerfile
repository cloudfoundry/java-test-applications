FROM openjdk:8-jdk-alpine

RUN apk add --no-cache --update \
    bash \
    curl \
    git \
    libstdc++

RUN curl http://repo.spring.io/snapshot/org/springframework/boot/spring-boot-cli/2.0.0.BUILD-SNAPSHOT/spring-boot-cli-2.0.0.BUILD-SNAPSHOT-bin.tar.gz | tar xzf - -C $HOME \
 && ln -s $HOME/spring-2.0.0.BUILD-SNAPSHOT/bin/spring /usr/local/bin
