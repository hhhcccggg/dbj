#!/bin/bash
git pull
mvn package
supervisorctl stop apigatewayserver
cp ./target/apigateway.jar ~/apigatewayserver/
supervisorctl start apigatewayserver
echo "OK"