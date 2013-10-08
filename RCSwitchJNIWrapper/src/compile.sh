#!/bin/sh

# A shell script to compile the libs on the Pi

rm *.so
rm *.o
rm send

#install wiringPi (optional)
cd wiringPi
#./build
cd ..

#compile java and make header file
echo -n "compiling java and creating c++ header file..."
javac com/opitz/jni/NativeRCSwitchAdapter.java
echo "..."
javah com.opitz.jni.NativeRCSwitchAdapter
echo "done"

#build libWiringPi.so
echo -n "building c++ library...."
g++ -c -I/usr/jdk1.8.0/include -I/usr/jdk1.8.0/include/linux com_opitz_jni_NativeRCSwitchAdapter.cpp RCSwitch.cpp
g++ -shared  /usr/local/lib/libwiringPiDev.so /usr/local/lib/libwiringPi.so  com_opitz_jni_NativeRCSwitchAdapter.o RCSwitch.o  -o libRCSwitchAdapter.so
echo "done"

#move library to installation path
mv libRCSwitchAdapter.so /usr/local/lib/libRCSwitchAdapter.so


###only used for testing purposes...
# build send
#g++ -c send.cpp
#g++ RCSwitch.o send.o -Wall -lwiringPi -o send

#build testLED
#g++ testLED.cpp RCSwitch.cpp -lwiringPi -o testLED


