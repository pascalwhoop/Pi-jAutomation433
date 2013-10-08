package com.opitz.jni;

import java.lang.reflect.Field;

/**
 * User: Pascal
 * Date: 26.09.13
 * Time: 15:01
 */
public class  NativeRCSwitchAdapter {

    private static final NativeRCSwitchAdapter instance = new NativeRCSwitchAdapter();

    public static NativeRCSwitchAdapter getInstance() {
        return instance;
    }

    private NativeRCSwitchAdapter() {
    }

    ;


    static {
        try {
            System.setProperty( "java.library.path", "/usr/local/lib" );
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print(System.getProperty("java.library.path"));
        System.loadLibrary("RCSwitchAdapter");
        System.load("/usr/local/lib/libRCSwitchAdapter.so");
    }

    // methods to redirect to native layer (C++)

    public native void sendBinary(String binaryEncodedSignal);

    public native void switchOn(String group, String channel);

    public native void switchOff(String group, String channel);

    public native void setPulseLength(int lengthInMilliseconds);

    public native void enableTransmitPin(int pinNumber);

    public native void setRepeatTimes(int repeatTimes);

}
