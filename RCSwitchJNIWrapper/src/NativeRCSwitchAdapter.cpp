#include "NativeRCSwitchAdapter.h"
#include "RCSwitch.h"
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

JNIEXPORT void JNICALL Java_NativeRCSwitchAdapter_switchOn(JNIEnv * env, jobject obj, jstring jsGroup, jstring jsChannel ){
    cout<<"teststring output"<<endl;
    const char *csGroup = env->GetStringUTFChars(jsGroup, 0);
    const char *csChannel = env->GetStringUTFChars(jsChannel, 0);

    char sGroup[6];
    char sChannel[6];

    for (int i = 0; i<5; i++) {
        sGroup[i] = csGroup[i];
        sChannel[i] = csChannel[i];
    }
    sGroup[5] = '\0';
    sChannel[5] = '\0';

    cout<<"ONON"<<endl;
    cout<<sGroup<<endl;
    cout<<sChannel<<endl;


    RCSwitch mySwitch = new RCSwitch();

    //for testing purposes set to the ELRO Power Plugs
    mySwitch.setPulseLength(300);
    mySwitch.enableTransmit(0);
    mySwitch.setRepeatTransmit(3);

    mySwitch.switchOn(sGroup, sChannel);


}

JNIEXPORT void JNICALL Java_NativeRCSwitchAdapter_switchOff(JNIEnv * env, jobject obj, jstring jsGroup, jstring jsChannel )
{
    const char *csGroup = env->GetStringUTFChars(jsGroup, 0);
    const char *csChannel = env->GetStringUTFChars(jsChannel, 0);

    char sGroup[6];
    char sChannel[6];

    for (int i = 0; i<5; i++) {
        sGroup[i] = csGroup[i];
        sChannel[i] = csChannel[i];
    }
    sGroup[5] = '\0';
    sChannel[5] = '\0';

    cout<<"OFFOFF"<<endl;
    cout<<sGroup;
    cout<<sChannel;

    RCSwitch mySwitch = RCSwitch();

    //for testing purposes set to the ELRO Power Plugs
    mySwitch.setPulseLength(300);
    mySwitch.enableTransmit(0);
    mySwitch.setRepeatTransmit(3);

    mySwitch.switchOff(sGroup, sChannel);


}
