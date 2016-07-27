#!/usr/bin/env sh

set -e

export TERM=${TERM:-dumb}

git clone java-test-applications java-test-applications-built

java -version

cd java-test-applications-built
./gradlew -Dorg.gradle.native=false build -x test
