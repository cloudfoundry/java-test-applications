#!/usr/bin/env sh

set -e -u

export TERM=${TERM:-dumb}

ln -fs $PWD/gradle $HOME/.gradle

git clone java-test-applications java-test-applications-built

cd java-test-applications-built
./gradlew -Dorg.gradle.native=false build -x test
