#!/bin/sh

# A shell script to compile the libs on the Pi

rm *.so
rm *.o
rm send

install wiringPi (optional)
cd wiringPi
./build
cd ..

#build libWiringPi.so
g++ -c -I/usr/jdk1.8.0/include -I/usr/jdk1.8.0/include/linux NativeRCSwitchAdapter.cpp RCSwitch.cpp
g++ -shared  /usr/local/lib/libwiringPiDev.so /usr/local/lib/libwiringPi.so  NativeRCSwitchAdapter.o RCSwitch.o  -o libRCSwitchAdapter.so
echo -n "build libWiringPi.so done"

#move library to installation path
mv libRCSwitchAdapter.so /usr/local/lib/libRCSwitchAdapter.so


###only used for testing purposes...
# build send
#g++ -c send.cpp
#g++ RCSwitch.o send.o -Wall -lwiringPi -o send

#build testLED
#g++ testLED.cpp RCSwitch.cpp -lwiringPi -o testLED


