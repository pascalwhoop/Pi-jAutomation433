# Installation details for prototype

## Spring Raspberry (Raspbian)

### Install Raspbian:
* Download current distribution of Raspbian [Raspberrypi Downloads](http://www.raspberrypi.org/downloads/)
* Install on SD-Card
* Expand File-System
* Set Password
* Connect Pi to Wifi via wpa_supplicant file (/etc/wpa_supplicant/wpa_supplicant.conf)
* SSH connect to Pi

### Install Oracle Java 8 ARM (optional)
Raspian wheezy already includes Java 1.7.0u40 but then you need to build for v7 which i havent tried yet, only for v8

* Download [Java 8 SE ](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-arm-downloads-2187472.html)
* Unzip and move to /usr/local
* Add <java>/bin to PATH with ~/.bash_profile file
* check if java version is working with `java -version`
	* expect to see `Java(TM) SE Runtime Environment (build 1.8.0-b132)`

### Install Jetty
* Download Jetty-9 [jetty download](http://download.eclipse.org/jetty/)
* extract to /usr/local/jetty-9
* follow instructions [here](http://pietervogelaar.nl/ubuntu-12-04-install-jetty-9) (consider different path for jetty)

### Create application .war
* Clone git repo to computer [repo](github.com/Ineffective/Pi-jAutomation433)
* edit hibernate.properties and change `validate` with `create`(only run it this way once)
* build with maven
* move .war to Pi
	* `/usr/local/jetty-9/webapps`

### Install local shared libraries
* clone repo somewhere on pi (or copy from pc with sftp)
* chmod +x compile.sh
* edit compile.sh and uncomment wiringPi build
* if you didnt install java at /usr/local/jdk1.8.0 make sure to modify compile.sh (the includes in the C compile part)
* execute (sudo)

start jetty server `sudo service jetty start`








	 	