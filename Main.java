import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Option> options = new ArrayList<Option>();

        options.add(
                new Option("Test print",
                    new Option.ExecClass() {
                        public void run() {
                            System.out.println("test");
                        }}));

        options.add(
                new Option("Low to high order",
                    new Option.ExecClass() {
                        public void run() {
                            IntByReference.test();
                        }}));

        options.add(
                new Option("Reverse Matrix",
                    new Option.ExecClass() {
                        public void run() {
                            Matrix.test();
                        }}));

        Menu.menu(options);
    }
}

