Pi-jAutomation433
=================

LOOKOUT! WORK IN PROGRESS. This not even close to a usable prototype yet! lets call it v.0.0001 ;-)

JVM with Spring MVC + Rule/BPM Engine + JNI to access C library controlling 433mhz plugs


install wiringPi first!!!

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
