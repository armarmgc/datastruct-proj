// Enum for operators with precendence and symbol that represents the operator
public enum Operator {
	ADD    (4, '+'),
	SUB    (4, '-'),
	MUL    (3, '*'),
	DIV    (3, '/'),
	MOD    (3, '%'),
	POW    (1, '^'),
	OPENP  (0, '('),
	CLOSEP (0, ')');

	public final int precedence;
	public final char symbol;

	// Constructor
	Operator(int precedence, char symbol) {
		this.precedence = precedence;
		this.symbol = symbol;
	}
	
	// Get operator from char
	public static Operator get(char val) {
		switch (val) {
			case '+':
				return ADD;
			case '-':
				return SUB;
			case '*':
				return MUL;
			case '/':
				return DIV;
			case '%':
				return MOD;
			case '^':
				return POW;
			case '(':
				return OPENP;
			case ')':
				return CLOSEP;
			default:
				System.out.println("Not a valid operator");
				return null;
		}
	}

	// Compute value of operation with x and y as operands
	public int compute(int x, int y) {
		switch(this) {
			case ADD:
				return x + y;
			case SUB:
				return x - y;
			case MUL:
				return x * y;
			case DIV:
				return x / y;
			case MOD:
				return x % y;
			case POW:
				return (int) Math.pow(x, y);
			default :
				return 0;
		}
	}

	// Return symbol that represents the operator
	public String toString() {
		return Character.toString(this.symbol);
	}

	public boolean isPrecedent(Operator other) {
		return this.precedence <= other.precedence;
	}
}

