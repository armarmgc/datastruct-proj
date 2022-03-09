public class IntByReference {
    private int val;

    public IntByReference(int val) {
        this.val = val;
    }

    public String toString() {
        return String.valueOf(this.val);
    }
    
    public int getVal() {
        return val;
    }
    
    public void setVal(int val) {
        this.val = val;
    }

    public void swapToLowHighOrder(IntByReference b) {
        if (this.getVal() > b.getVal()) {
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
