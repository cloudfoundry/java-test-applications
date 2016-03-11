#!/usr/bin/env bash

set -e

pushd java-test-applications
  ./gradlew build
popd
