public class Matrix {
    private final int[][] matrix;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public String toString() {
        String str = "";

        for (int[] m : matrix) {
            for (int x : m) {
                if (x >= 0) {
                    str = str.concat(String.valueOf(x) + " ");
                } else {
                    str = str.concat("  ");
                }

            }

            str = str.substring(0, str.length() - 1);
            str = str.concat("\n");
        }

        str = str.concat(new StringBuilder(str).reverse().toString());

        str = str.concat("\n");

        return str;
    }

    static int[][] keypad() {
        return new int[][]{
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 9 },
            {-1, 0, -1},
        };
    }

    static int[][] numbers() {
        return new int[][]{
            { 0, 1 },
            { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
        };
    }

    public static void test() {
        Matrix m0 = new Matrix(keypad());
        System.out.println("Keypad:");
        System.out.println(m0);

        Matrix m1 = new Matrix(numbers());
        System.out.println("Numbers Systems:");
        System.out.println(m1);
    }

    public static void main(String[] args) {
        test();
    }

}

