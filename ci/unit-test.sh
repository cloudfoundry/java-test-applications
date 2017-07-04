#!/usr/bin/env sh

set -e -u

export TERM=${TERM:-dumb}

ln -fs $PWD/gradle $HOME/.gradle

cd java-test-applications
./gradlew -Dorg.gradle.native=false build
