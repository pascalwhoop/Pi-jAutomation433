Pi-jAutomation433
=================

LOOKOUT! WORK IN PROGRESS. This may be close to a usable prototype. But still lets call it v.0.1 ;-)

JVM with Spring MVC + Rule/BPM Engine + JNI to access C library controlling 433mhz plugs

##install

###Backend

#####shared libs and wiringPi
to install cd to /RCSwitchJNIWrapper/src and run 
```
sudo ./compile.sh
```
This should install our library to /usr/local/lib
If you need wiringPi as well uncomment the line saying "#./build" to build and install wiringPi as well.

##### Build and deploy to server
I use a jetty on my pi. I also use java se 1.8 hard float. Then just build the project using maven on a normal pc with the specified jdk and drop the .war on your server. 

It should then boot up, find the native libs in /usr/local/lib and be able to control the plugs. 

##### Connecting the 433mhz sender the right way
Refer to this:
[wiringPi Pins explained](https://projects.drogon.net/raspberry-pi/wiringpi/pins/)

Connect the Data cable to wiringPi pin no. 0 !! 



### Frontend
The Frontend is based on Ionic and I use yeoman, bower and grunt. To get the frontend running do:
`npm install` in the frontend folder. then do `bower install` as well. This should install all neccessary things. If you havent installed the upper mentioned tools yet make sure to do so (just google them and you'll find out how)

you can use `grunt server` to preview the UI or deploy it to your device of choice. be aware that the code won't be able to access the backend if you don't disable the websecurity in chrome. to disable start chrome like this (mac):
`open -a Google\ Chrome --args --disable-web-security`
Otherwise just deploy to a device like `ionic run android`

## Starting the server



### Structure and Frameworks

* AngularJS and ionic for the Mobile Frontend
* Yeoman (yo, bower, grunt) for the scaffolding, js dependency mgmt and build of the frontend
* Maven based for the backend
* Spring, Hibernate, Jackson

###Development

I use IntelliJ as an IDE. I have a Project on the root, and 3 modules. One for the entire project (which is sort of the backend), one for the RCSwitchJNIWrapper and one for the www Folder in the Phonegap (Frontend) Folder.

I deploy to the mobile device via phonegap command line (i have a run config in intellij for that).

For testing purposes of the backend without the need to actually access jni I deploy to a local jetty which I can debug and up faster than on the pi

For ad hoch UI tests i run "grunt server" within the Frontend folder which hosts my UI

### Short description
This project is supposed to bring a powerful middleware environment to the Pi and make "stupid" 433mhz plugs very smart.

* There will be a Spring MVC Application running in a JVM (J SE 8 ARM optimized hardware float)
* There should be a rule engine coming + a BPM engine
* You should be able to create profiles, groups, timers, routines for your plugs
* Everything will be accessible via REST
	* adding plugs (name, location, image)
	* create groups
	* create schedules, timers (turn off in …)
	* create profiles, access restrictions (guest users)


There is a "lower level" implementation underneath java which is written in C++. This accesses the Raspberry Pi GPIO pins to send signals via a 433mhz Transmitter. 
Thanks to [RCSwitch](https://code.google.com/p/rc-switch/) for this implementation. Actually the code used is from [RCSwitch-Pi](https://github.com/r10r/rcswitch-pi) which replaced the libs to make it work on the pi. 

#### Test send with the RCSwitch

I modified RCSwitch to accept any switch configuration. So now you could send a signal to a 
plug configured as such:

11001 10101

```
cd RCSwitchJNIWrapper/src/RCSwitch-Pi 
make
send 11010 11001 1 #your own plug configuration
```
Of course for this to work you have to have a 433mhz module attached to your pi. I have it attached to GPIO pin #17 (wiringPi numbering 0)




Low level Blog documentation to show progress:
[http://androautomation.blogspot.de/](http://androautomation.blogspot.de/)
