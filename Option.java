public class Option {
    public interface ExecClass {
        public void run();
    }

    private static int index;

    private int idx = 0;
    private String name;
    private ExecClass exec;

    public Option(String name, ExecClass exec) {
        this.idx  = index;
        this.name = name;
        this.exec = exec;

        index++;
    }

    public int getIdx() {
        return idx;
    }

    public String getName() {
        return name;
    }

    public void run() {
        exec.run();
        return;
    }
}

