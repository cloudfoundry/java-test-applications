FROM openjdk:8-jdk

RUN apt-get update && apt-get install --no-install-recommends -y \
    curl \
 && rm -rf /var/lib/apt/lists/*

RUN curl https://repo.spring.io/release/org/springframework/boot/spring-boot-cli/2.6.7/spring-boot-cli-2.6.7-bin.tar.gz | tar xzf - -C $HOME \
 && ln -s $HOME/spring-2.6.7/bin/spring /usr/local/bin
