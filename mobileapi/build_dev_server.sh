#!/bin/bash
git pull
mv ./src/main/resources/application.dev.server.properties ./src/main/resources/application.properties
echo "拷贝配置文件结束"
echo "开始jar安装到本地maven"
mvn install:install-file -Dfile=./libs/youzan/open-sdk-java-2.0.2.jar  -DgroupId=com.youzanyun -DartifactId=open-sdk-java -Dversion=1.0
echo "按照jar到本地maven"
mvn package
supervisorctl stop apiserver
cp ./target/api.jar ~/apiserver/
supervisorctl start apiserver
git reset --hard
echo "OK"