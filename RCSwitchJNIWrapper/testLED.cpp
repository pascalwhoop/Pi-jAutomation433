#include <wiringPi.h>
#include "RCSwitch.h"

main(void) {

  wiringPiSetup ();

  //test LED
  pinMode (0, OUTPUT);
  digitalWrite (0, HIGH) ; delay (500) ;
  digitalWrite (0,  LOW) ; delay (500) ;

  RCSwitch mySwitch = RCSwitch();
  mySwitch.setPulseLength(300);
  mySwitch.enableTransmit(PIN);
  mySwitch.setRepeatTransmit(4);

  mySwitch.switchOn("10101", "11111");


}
