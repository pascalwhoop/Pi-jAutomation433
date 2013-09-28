DESCRIPTION = "RCSwitch on Raspberry Pi"
LICENSE = "GPL"
VERSION = 1.0

CXXFLAGS += -Wall
CXXFLAGS += -lwiringPi

send: RCSwitch.o send.o
	$(CXX) $+ -o $@ $(CXXFLAGS) $(LDFLAGS)

clean:
	rm -f *.o send
