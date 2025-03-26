#!/usr/bin/env bash

mkdir -p ./cache/
FILE=./cache/openapi-generator-cli.jar
if [ ! -f $FILE ]; then
  wget https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/7.12.0/openapi-generator-cli-7.12.0.jar -O $FILE
fi;

# https://openapi-generator.tech/docs/generators/javascript/
# https://openapi-generator.tech/docs/usage/#generate
java -jar $FILE generate --generator-name javascript \
  -i ./spec/url-shortener.yaml \
  -o ./cache/javascript/ \
  --package-name ro.polak.urlshortener \
  --additional-properties=library=apollo