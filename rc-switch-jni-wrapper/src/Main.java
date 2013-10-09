import com.opitz.jni.NativeRCSwitchAdapter;

public class Main {

    public static void main(String[] args) {

        NativeRCSwitchAdapter adapter = NativeRCSwitchAdapter.getInstance();

        //adapter.sendBinary("1010101011");

        adapter.switchOn("11111", "10101");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        adapter.switchOff("11111", "10101");


    }
}
