#!/bin/sh
set -eu

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
BUILD_DIR="$SCRIPT_DIR/build/converter-verification"
SOURCE_DIR="$SCRIPT_DIR/app/src/main/java/com/likuamare/geeznumerals"

mkdir -p "$BUILD_DIR"
javac -encoding UTF-8 -d "$BUILD_DIR" \
    "$SOURCE_DIR/EthiopicNumeralConverter.java" \
    "$SCRIPT_DIR/verification/ConverterSpecTest.java"
java -cp "$BUILD_DIR" com.likuamare.geeznumerals.ConverterSpecTest
