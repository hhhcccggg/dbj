#!/bin/bash

protoc -I=./protobuf/mq --java_out=./src/main/java ./protobuf/mq/QueueWorkInfo.proto
echo "生成成功"