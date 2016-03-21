#!/usr/bin/env sh

set -e

export TERM=${TERM:-dumb}

cd java-test-applications
./gradlew -Dorg.gradle.native=false build
