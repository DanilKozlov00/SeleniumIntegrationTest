#!/bin/bash
TMP=$(curl https://github.com/DanilKozlov00/SeleniumIntegrationTest/releases/latest/)
CURRENT_TAG="$(echo $TMP| tr "qwertyuiop[]asdfghjkl;'zxcvbnm,./<>QWERTYUIOP{}ASDFGHJKL:ZXCVBNM=" "\n")"
cd / 
cd /bin/18-VTbo-1b/LR/03/
java -jar Selen-0.1-jar-with-dependencies.jar
TAG=$CURRENT_TAG
echo $CURRENT_TAG
echo $TAG
while [ "$CURRENT_TAG" = "$TAG" ] 
do
sleep 1000000
TAG=$(curl https://github.com/DanilKozlov00/SeleniumIntegrationTest/releases/latest/)
done
wget https://github.com/DanilKozlov00/SeleniumIntegrationTest/releases/latest/download/18-VTbo-1b
systemctl stop TRPO_LR3
rm -r 18-VTbo-1b
unrar x 18-VTbo-1b.rar 
mv 18-VTbo-1b /bin
rm 18-VTbo-1b.rar
systemctl daemon-reload
systemctl start TRPO_LR3
systemctl enable TRPO_LR3



