FROM openjdk:8-jdk

RUN apt-get update && apt-get install --no-install-recommends -y \
    curl \
 && rm -rf /var/lib/apt/lists/*

RUN curl https://repo.spring.io/release/org/springframework/boot/spring-boot-cli/1.5.20.RELEASE/spring-boot-cli-1.5.20.RELEASE-bin.tar.gz | tar xzf - -C $HOME \
 && ln -s $HOME/spring-1.5.20.RELEASE/bin/spring /usr/local/bin
