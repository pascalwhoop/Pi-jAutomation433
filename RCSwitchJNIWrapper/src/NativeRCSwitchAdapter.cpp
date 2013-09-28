#include "NativeRCSwitchAdapter.h"
#include "RCSwitch-Pi/RCSwitch.h"
#include <stdio.h>
#include <iostream>

using namespace std;



JNIEXPORT void JNICALL Java_NativeRCSwitchAdapter_sendBinary (JNIEnv *env, jobject obj, jstring javaString)
{
    //Get the native string from javaString
    const char *nativeString = env->GetStringUTFChars(javaString, 0);
    //Do something with the nativeString
    cout<<nativeString<<endl;




    //DON'T FORGET THIS LINE!!!
    env->ReleaseStringUTFChars(javaString, nativeString);
}

JNIEXPORT void JNICALL Java_NativeRCSwitchAdapter_switchOn(JNIEnv *, jobject, jstring jsGroup, jstring jsChannel )
{
    const char *sGroup = env->GetStringUTFChars(jsGroup, 0);
    const char *sChannel = env->GetStringUTFChars(jsChannel, 0);
    RCSwitch mySwitch = RCSwitch();

    //for testing purposes set to the ELRO Power Plugs
    mySwitch.setPulseLength(300)
    mySwitch.enableTransmit(0);
    mySwitch.setRepeatTransmit(3);

    mySwitch.switchOn(sGroup, sChannel);


}

JNIEXPORT void JNICALL Java_NativeRCSwitchAdapter_switchOff(JNIEnv *, jobject, jstring jsGroup, jstring jsChannel )
{
    const char *sGroup = env->GetStringUTFChars(jsGroup, 0);
    const char *sChannel = env->GetStringUTFChars(jsChannel, 0);
    RCSwitch mySwitch = RCSwitch();

    //for testing purposes set to the ELRO Power Plugs
    mySwitch.setPulseLength(300)
    mySwitch.enableTransmit(0);
    mySwitch.setRepeatTransmit(3);

    mySwitch.switchOff(sGroup, sChannel);


}
