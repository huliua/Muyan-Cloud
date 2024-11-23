#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}
echo "begin clear jars "
rm -rf ./jars/*.jar

# copy jar
echo "begin copy muyan-auth "
cp ./muyan-auth/target/muyan-auth.jar ./jars

echo "begin copy muyan-gateway "
cp ./muyan-gateway/target/muyan-gateway.jar ./jars

echo "begin copy muyan-system "
cp ./muyan-modules/muyan-system/target/muyan-system.jar ./jars

echo "begin copy muyan-codeShare "
cp ./muyan-modules/muyan-codeShare/target/muyan-codeShare.jar ./jars