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
        String path = NativeRCSwitchAdapter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.load(path + "NativeRCSwitchAdapter.so");
        //System.load(path + "")

    }

    // methods to redirect to native layer (C++)

    public native void sendBinary(String binaryEncodedSignal);

    public native void switchOn(String group, String channel);
    public native void switchOff(String group, String channel);

    public native void setPulseLength(int lengthInMilliseconds);

    public native void enableTransmitPin(int pinNumber);

    public native void setRepeatTimes(int repeatTimes);

}
