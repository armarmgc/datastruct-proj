import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static void menu(ArrayList<Option> opts) {
        // Initialize help string
        String help = "";

        // Start border
        help = help.concat("----------\n");

        // Keep track of last index to add menu option after
        int last = 0;

        // Add each option to the help menu
        for (Option opt : opts) {
            help = help.concat(
                    String.format(
                        "%d - %s\n",
                        opt.getIdx(),
                        opt.getName()));

            last = opt.getIdx();
        }

        // Add print menu to the end
        help = help.concat(
                String.format(
                    "? - %s\n",
                    "Print Menu"));

        // End border
        help = help.concat("----------");

        // Print out the help menu
        System.out.println(help);

        // Print out the menu in a loop
        while (true) {
            // Print out the prompt
            System.out.print("> ");

            Scanner scan = new Scanner(System.in);

            // Get a single byte and subtract to convert the ascii to int
            int selection = scan.next().getBytes()[0] - 0x30;

            // Help menu - ? is hex code 0x3, 0xf + 0x30 = 0x3f
            // Other options - 0 <= selection <= opts.size() (assert selection is a valid option)
            if (selection == 0xf) {
                // Print help
                System.out.println(help);
            } else if (selection < opts.size() && selection >= 0) {
                // Run the run method on the selected Option
                opts.get(selection).run();
            } else {
                // Not a valid option
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

