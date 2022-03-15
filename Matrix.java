// 2d array class able to print the forward and reverse representations
public class Matrix {
    // 2d array
    private final int[][] matrix;

    // Constructor
    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public String toString() {
        // Intiliaze the string value of the matrix, so we can reverse it after
        String str = "";

        // Loop through each row
        for (int[] m : matrix) {
            // Loop through each column of the row
            for (int x : m) {
                // Print the character if it is not negative, otherwise print ' '
                if (x >= 0) {
                    str = str.concat(String.valueOf(x) + " ");
                } else {
                    str = str.concat("  ");
                }
            }

            // Trim the last space from the line for reversing the string later
            str = str.substring(0, str.length() - 1);
            
            // Newline for next row
            str = str.concat("\n");
        }

        // Add the reverse to the string
        str = str.concat(new StringBuilder(str).reverse().toString());

        // Newline after
        str = str.concat("\n");

        return str;
    }

    // Static keypad data
    static int[][] keypad() {
        return new int[][]{
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 9 },
            {-1, 0, -1},
        };
    }

    // Static numbers data
    static int[][] numbers() {
        return new int[][]{
            { 0, 1 },
            { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
        };
    }

    // Test function for reversing keypad and matrix
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

