#!/usr/bin/env bash
mvn clean install
# shellcheck disable=SC1001
java -jar target\dron-rest-1.0-SNAPSHOT.jar
