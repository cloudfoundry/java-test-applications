#!/usr/bin/env sh

set -e -u

export TERM=${TERM:-dumb}

cd java-test-applications
./gradlew -Dgradle.user.home=../gradle -Dorg.gradle.native=false build
