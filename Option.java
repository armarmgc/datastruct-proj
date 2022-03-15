// Option class for each option in the menu
// name - name of the option, shown on the help menu
// exec - the exec class that stores the `run` function that is run when the
// option is chosen
public class Option {
    // Interface for the exec class
    // This interface contains a run function, that we can set in the constructor
    // using an anonymous class.
    //
    // An anonymous class is created on an interface a single time under no name.
    //
    // This allows us to create and pass in an anonymous class for each Option in the 
    // Menu very easily, without having to define each class, but instead define
    // them as we pass them in.
    //
    // Example (adding a new Option to the options ArrayList<Option>):
    //
    //  options.add(
    //          new Option("Test print",
    //              new Option.ExecClass() {  <-- Declaration of anonymous class
    //                  public void run() {   <-- declaring the run function
    //                      System.out.println("test");
    //                  }}));
    //
    public interface ExecClass {
        // Run function with no args or return value
        public void run();
    }

    // Counter for number of instances created
    private static int index = 0;

    // Idx for this instance
    private int idx;
    
    // Name for this option
    private String name;

    // Exec class
    private ExecClass exec;

    // Constructor
    public Option(String name, ExecClass exec) {
        this.idx  = index;
        this.name = name;
        this.exec = exec;

        // Increment instance counter
        index++;
    }

    // Getter for `idx`
    public int getIdx() {
        return idx;
    }

    // Getter for `name`
    public String getName() {
        return name;
    }

    // Wrapper run function on the exec class
    public void run() {
        exec.run();
    }
}

