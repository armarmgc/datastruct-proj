import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static void menu(ArrayList<Option> opts) {
        String help = "";

        help = help.concat("----------\n");

        int last = 0;

        for (Option opt : opts) {
            help = help.concat(
                    String.format(
                        "%d - %s\n",
                        opt.getIdx(),
                        opt.getName()));

            last = opt.getIdx();
        }

        help = help.concat(
                String.format(
                    "? - %s\n",
                    "Print Menu"));

        help = help.concat("----------");

        System.out.println(help);

        while (true) {
            System.out.print("> ");

            Scanner scan = new Scanner(System.in);

            int selection = scan.next().getBytes()[0] - 0x30;

            if (selection == 0xf) {
                System.out.println(help);
            } else if (selection < opts.size() && selection >= 0) {
                opts.get(selection).run();
            } else {
                System.out.println("Invalid option");
            }
        }
    }

    public static void test() {
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

        menu(options);
    }

    public static void main(String[] args) {
        test();
    }
}

