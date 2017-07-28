#!/bin/bash
mvn clean install
cp ./robots/Robot06.jar ~/robocode/robots
cd ~/robocode
java -Xmx1024M -cp libs/robocode.jar robocode.Robocode -battle battles/battles.battle -results results.txt
