#!/usr/bin/env sh

set -e -u

export TERM=${TERM:-dumb}

git clone java-test-applications java-test-applications-built

cd java-test-applications-built
./gradlew -Dgradle.user.home=../gradle -Dorg.gradle.native=false build -x test
