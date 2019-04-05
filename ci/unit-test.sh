#!/usr/bin/env bash

set -euo pipefail

export TERM=${TERM:-dumb}

[[ -d $PWD/gradle && ! -d $HOME/.gradle ]] && ln -s $PWD/gradle $HOME/.gradle

cd java-test-applications
./gradlew -Dorg.gradle.native=false build
