#!/usr/bin/env bash

set -e

export TERM=${TERM:-dumb}

pushd java-test-applications
  ./gradlew build
popd
