#!/bin/bash
cd /home/pi/cam/
sudo java -Djava.util.logging.config.file=./log.properties -classpath .:jrasp-0.0.1-SNAPSHOT.jar com.jrab.raspi.md.Driver 1 500