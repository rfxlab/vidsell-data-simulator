#!/bin/sh

JVM_PARAMS="-Xms512m -Xmx2048m -XX:+TieredCompilation -XX:+UseCompressedOops -XX:+DisableExplicitGC -XX:+UseNUMA -server"
JAR_NAME="vidsell-data-simulator-bot-1.0.jar"

/usr/bin/java -jar $JVM_PARAMS $JAR_NAME  >> /dev/null 2>&1 &

echo "start $JAR_NAME"
