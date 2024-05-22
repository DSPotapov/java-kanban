import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
//        TestFor4Sprint.runTest();

        ArrayList<String> strings = new ArrayList<>();
        strings.add("first");
        strings.add("second");
        strings.remove(0);
        System.out.println("strings = " + strings + " size = " + strings.size());

    }
}
