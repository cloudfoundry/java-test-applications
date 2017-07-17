#!/usr/bin/env sh

set -e -u

export TERM=${TERM:-dumb}

[[ -d $PWD/gradle && ! -d $HOME/.gradle ]] && ln -s $PWD/gradle $HOME/.gradle

cd java-test-applications
./gradlew -Dorg.gradle.native=false build
