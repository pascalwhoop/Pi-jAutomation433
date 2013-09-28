public class Main {

    public static void main(String[] args) {

        NativeRCSwitchAdapter adapter = new NativeRCSwitchAdapter();

        adapter.switchOn("11111", "11111");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        adapter.switchOff("11111", "11111");
    }
}
