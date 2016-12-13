FROM openjdk:8-jdk-alpine

RUN apk add --update \
    bash \
    curl \
    git \
    libstdc++ \
 && rm -rf /var/cache/apk/*

RUN curl http://repo.spring.io/release/org/springframework/boot/spring-boot-cli/1.4.1.RELEASE/spring-boot-cli-1.4.1.RELEASE-bin.tar.gz | tar xzf - -C $HOME \
 && ln -s $HOME/spring-1.4.1.RELEASE/bin/spring /usr/local/bin
