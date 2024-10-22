#!/usr/bin/env bash
host=boomroom

ssh -t pi@$host -o StrictHostKeyChecking=no "mkdir /home/pi/com"
ssh -t pi@$host -o StrictHostKeyChecking=no "mkdir /home/pi/com/bigboxer23"
ssh -t pi@$host -o StrictHostKeyChecking=no "mkdir /home/pi/com/bigboxer23/scd41-service"
ssh -t pi@$host -o StrictHostKeyChecking=no "mkdir /home/pi/com/bigboxer23/scd41-service/1.0.0"
mvn package -DskipTests

scp -o StrictHostKeyChecking=no -r scd41-service.service pi@$host:~/
ssh -t pi@$host -o StrictHostKeyChecking=no "sudo mv ~/scd41-service.service /lib/systemd/system"
ssh -t pi@$host -o StrictHostKeyChecking=no "sudo systemctl daemon-reload"
ssh -t pi@$host -o StrictHostKeyChecking=no "sudo systemctl enable scd41-service.service"
ssh -t pi@$host -o StrictHostKeyChecking=no "sudo systemctl start scd41-service.service"