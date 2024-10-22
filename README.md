[![CodeQL](https://github.com/bigboxer23/scd41-service/actions/workflows/codeql.yml/badge.svg)](https://github.com/bigboxer23/scd41-service/actions/workflows/codeql.yml)

## Introduction

Sprint Boot WebService exposing data from a SCD41 CO2/Temperature/Humidity Sensor running on a Raspberry Pi.

An averaged reading of CO2, temperature (C), and relative humidity (%) from a SCD41 (`https://www.adafruit.com/product/5190`)
via a web url returning JSON formatted data.  This requires the installation of
python, java and the `adafruit-circuitpython-scd4x` library via pip.

Other good resources on the sensor: `https://github.com/adafruit/Adafruit_CircuitPython_SCD4x` and `https://github.com/Sensirion/raspberry-pi-i2c-scd4x`

## Installation


* Install `adafruit-circuitpython-scd4x` Library (See for alternate installation instructions https://github.com/adafruit/Adafruit_CircuitPython_SCD4x)
  * `sudo apt-get install python3-pip`
  * `sudo pip3 install adafruit-circuitpython-scd4x`
  * `pip3 install circup`
  * `circup install adafruit_scd4x`
* Install java
  * `sudo apt install default-jdk`
* Create application.properties in `src/main/resources`
  * Example Content: 
  ```server.port: 8080
    logbackserver:192.168.0.7:5671
    server.port: 8080
    host=boomroom
    sensorName=boomroom
  
* Note: if not using a logback server, update the `logback.xml` to remove the `stash` appender
* run `install.sh` script to transfer install the .jar, service definition, & install the service

