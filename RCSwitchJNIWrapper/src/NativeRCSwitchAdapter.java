/**
 * User: Pascal
 * Date: 26.09.13
 * Time: 15:01
 */
public class NativeRCSwitchAdapter {

    static{
        String path = NativeRCSwitchAdapter.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        System.load(path + "NativeRCSwitchAdapter.so");
    }

    // methods to redirect to native layer (C++)

    public native void sendBinary(String binaryEncodedSignal);

    public native void setPulseLength(int lengthInMilliseconds);

    public native void enableTransmitPin(int pinNumber);

    public native void setRepeatTimes(int repeatTimes);
}
