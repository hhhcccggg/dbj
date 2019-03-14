#!/bin/bash

echo "开始自动化打包"
echo "开始jar安装到本地maven"
mvn install:install-file -Dfile=./libs/youzan/open-sdk-java-2.0.2.jar  -DgroupId=com.youzanyun -DartifactId=open-sdk-java -Dversion=1.0
echo "安装jar到本地maven"

echo "开始拷贝环境配置文件"
mv ./adminserver/src/main/resources/bootstrap-production.properties ./adminserver/src/main/resources/bootstrap.properties
mv ./mobileapi/src/main/resources/bootstrap-production.properties ./mobileapi/src/main/resources/bootstrap.properties
mv ./microservice/cfgserver/src/main/resources/application-production.properties ./microservice/cfgserver/src/main/resources/application.properties
echo "配置文件拷贝结束"

mvn package

supervisorctl stop adminserverapp
supervisorctl stop apiserverapp
supervisorctl stop cfgserver

cp ./adminserver/target/api.jar /root/serverapps/adminserverapp/admin.jar
cp ./mobileapi/target/api.jar /root/serverapps/apiserverapp/
cp ./microservice/cfgserver/target/cfgserver.jar /root/serverapps/cfgserver/

supervisorctl start cfgserver
supervisorctl start adminserverapp
supervisorctl start apiserverapp

git reset --hard
echo "OK"