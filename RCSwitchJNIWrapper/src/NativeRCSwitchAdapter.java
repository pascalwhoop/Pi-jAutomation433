/**
 * User: Pascal
 * Date: 26.09.13
 * Time: 15:01
 */
public class NativeRCSwitchAdapter {

    private static final NativeRCSwitchAdapter instance = new NativeRCSwitchAdapter();

    public static NativeRCSwitchAdapter getInstance(){
        return instance;
    }
    private NativeRCSwitchAdapter(){};


    static{
        System.loadLibrary("RCSwitchAdapter");
    }

    // methods to redirect to native layer (C++)

    public native void sendBinary(String binaryEncodedSignal);

    public native void switchOn(String group, String channel);
    public native void switchOff(String group, String channel);

    public native void setPulseLength(int lengthInMilliseconds);

    public native void enableTransmitPin(int pinNumber);

    public native void setRepeatTimes(int repeatTimes);

}
