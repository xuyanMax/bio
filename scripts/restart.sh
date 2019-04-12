#!/bin/bash

tomcat="/home/chgc/apache-tomcat-9.0.8"
webapps=$tomcat/webapps
log=$tomcat/log
tomcat_bin=$tomcat/bin
appname="tomcat"



# remove old ROOT and replace it with questionnaire dir

# backup ROOT
cd $webapps
timestamp=$(date +%s)
mv ROOT "ROOT_bak_$timestamp"
mv questionnaire ROOT
# backup ROOT end

echo "========killing tomcat========"
#grep排除grep这个进程
PID=$(ps -ef | grep $appname | grep -v grep | awk '{print $2}')

for var in ${PID[@]};
do
	echo "loop pid=$var"
	kill -9 $var
done

echo "========killing tomcat done========="

cd $bin

sh startup.sh

echo "tomcat started successfully."


