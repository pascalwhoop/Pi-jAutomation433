Pi-jAutomation433
=================

JVM with Spring MVC + Rule/BPM Engine + JNI to access C library controlling 433mhz plugs


### Short description
This project is supposed to bring a powerfull middleware environment to the Pi and make "stupid" 433mhz plugs very smart. 

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




Low level Blog documentation to show progress:
[http://androautomation.blogspot.de/](http://androautomation.blogspot.de/)
