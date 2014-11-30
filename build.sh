#!/bin/sh
rm app.jar
./gradlew clean assemble
cp ./app/build/libs/app.jar ./
