#!/bin/bash

echo "开始自动化打包"
echo "开始jar安装到本地maven"
mvn install:install-file -Dfile=./libs/youzan/open-sdk-java-2.0.2.jar  -DgroupId=com.youzanyun -DartifactId=open-sdk-java -Dversion=1.0
echo "安装jar到本地maven"

echo "开始拷贝环境配置文件"
mv ./adminserver/src/main/resources/application.dev.server.properties ./adminserver/src/main/resources/application.properties
mv ./mobileapi/src/main/resources/application.dev.server.properties ./mobileapi/src/main/resources/application.properties
mv ./adminoperate/src/main/resources/application.dev.server.properties ./adminoperate/src/main/resources/application.properties
echo "配置文件拷贝结束"

mvn package

supervisorctl stop adminserver
supervisorctl stop apigatewayserver
supervisorctl stop apiserver
supervisorctl stop adminoperateserver

cp ./adminserver/target/api.jar ~/adminserver/admin.jar
cp ./mobileapi/target/api.jar ~/apiserver/
cp ./apigateway/target/apigateway.jar ~/apigatewayserver/
cp ./adminoperate/target/adminoperate.jar ~/adminoperateserver/

supervisorctl start adminserver
supervisorctl start apigatewayserver
supervisorctl start apiserver
supervisorctl start adminoperateserver

git reset --hard
echo "OK"