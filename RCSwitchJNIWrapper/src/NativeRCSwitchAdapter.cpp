#include "NativeRCSwitchAdapter.h"
#include "piRemote/RCSwitch.h"
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

