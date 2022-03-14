public class IntByReference {
    // Stored value
    private int val;

    // Constructor
    public IntByReference(int val) {
        this.val = val;
    }

    // String representation of val
    public String toString() {
        return String.valueOf(this.val);
    }
    
    // Getter for val
    public int getVal() {
        return val;
    }
    
    // Setter for val
    public void setVal(int val) {
        this.val = val;
    }

    // Swap `this` with IntByReference `b`, if `this` is greater than `b`
    public void swapToLowHighOrder(IntByReference b) {
        // Check if `this` is greater than `b`
        if (this.getVal() > b.getVal()) {
            // Swap `this` and `b`
            int tmp = b.getVal();
            b.setVal(this.getVal());
            this.setVal(tmp);
        }
    }

    public static void swapper(int x, int y) {
        IntByReference a = new IntByReference(x);
        IntByReference b = new IntByReference(y);

        System.out.println("Before: " + a + " " + b);

        a.swapToLowHighOrder(b);

        System.out.println("After: " + a + " " + b);

        System.out.print("\n");
    }

    public static void test() {
        IntByReference.swapper(21, 16);
        IntByReference.swapper(16, 21);
        IntByReference.swapper(16, -1);
    }

    public static void main(String[] ags) {
        test();
    }
}
