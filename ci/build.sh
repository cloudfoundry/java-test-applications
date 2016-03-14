#!/usr/bin/env bash

set -e

export TERM=${TERM:-dumb}

git clone java-test-applications java-test-applications-built

pushd java-test-applications-built
  ./gradlew build -x test
popd
