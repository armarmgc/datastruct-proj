// Token that represents an operator or a value
public class Token {
	public int val;
	public Operator op;

	// Is this token an operator
	public boolean is_op;
	
	// Constructor for value
	public Token(int val) {
		this.val = val;
		this.is_op = false;
	}

	// Constructor for operator
	public Token(Operator op) {
		this.op = op;
		this.is_op = true;
	}

	// Prints operator if `is_op` is set, otherwise print value
	public String toString() {
		if (!is_op) {
			return Integer.toString(val);
		}
		return op.toString();
	}
}

