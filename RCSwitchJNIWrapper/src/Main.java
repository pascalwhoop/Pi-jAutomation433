public class Main {

    public static void main(String[] args) {

        NativeRCSwitchAdapter adapter = new NativeRCSwitchAdapter();

        adapter.sendBinary("some Text From Java");
    }
}
