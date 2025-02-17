#!/usr/bin/env bash

set -euo pipefail

export TERM=${TERM:-dumb}

[[ -d $PWD/gradle && ! -d $HOME/.gradle ]] && ln -s $PWD/gradle $HOME/.gradle

git clone java-test-applications java-test-applications-built-cflinuxfs4

cd java-test-applications-built-cflinuxfs4
./gradlew -Dorg.gradle.native=false build -x test
