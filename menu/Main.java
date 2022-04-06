import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create nwe option list
        ArrayList<Option> options = new ArrayList<Option>();

        // Add test print
        options.add(
                new Option("Test print",
                    new Option.ExecClass() {
                        public void run() {
                            System.out.println("test");
                        }}));

        // Add low to high order
        options.add(
                new Option("Low to high order",
                    new Option.ExecClass() {
                        public void run() {
                            IntByReference.test();
                        }}));

        // Add reverse matrix
        options.add(
                new Option("Reverse Matrix",
                    new Option.ExecClass() {
                        public void run() {
                            Matrix.test();
                        }}));

        options.add(
                new Option("Gravity Calculate",
                        new Option.ExecClass() {
                            public void run() { Gravity.test(); }}));

        // Start menu in a loop
        Menu.menu(options);
    }
}

