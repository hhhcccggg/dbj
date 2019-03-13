#!/bin/bash

echo "开始自动化打包"
echo "开始jar安装到本地maven"
mvn install:install-file -Dfile=./libs/youzan/open-sdk-java-2.0.2.jar  -DgroupId=com.youzanyun -DartifactId=open-sdk-java -Dversion=1.0
echo "安装jar到本地maven"

echo "开始拷贝环境配置文件"
mv ./adminserver/src/main/resources/bootstrap-dev.properties ./adminserver/src/main/resources/bootstrap.properties
mv ./mobileapi/src/main/resources/bootstrap-dev.properties ./mobileapi/src/main/resources/bootstrap.properties
mv ./microservice/cfgserver/src/main/resources/application-dev.properties ./microservice/cfgserver/src/main/resources/application.properties
echo "配置文件拷贝结束"

mvn package

supervisorctl stop adminserver
supervisorctl stop apigatewayserver
supervisorctl stop apiserver
supervisorctl stop cfgserver

cp ./adminserver/target/api.jar /root/adminserver/admin.jar
cp ./mobileapi/target/api.jar /root/apiserver/
cp ./apigateway/target/apigateway.jar /root/apigatewayserver/
cp ./microservice/cfgserver/target/cfgserver.jar /root/cfgserver/

supervisorctl start cfgserver
supervisorctl start adminserver
supervisorctl start apigatewayserver
supervisorctl start apiserver

git reset --hard
echo "OK"