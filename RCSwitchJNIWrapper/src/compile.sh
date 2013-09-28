#!/bin/sh

# A shell script to compile the libs on the Pi


g++ -shared -I/usr/jdk1.8.0/include -I/usr/jdk1.8.0/include/linux NativeRCSwitchAdapter.cpp -o NativeRCSwitchAdapter.so
