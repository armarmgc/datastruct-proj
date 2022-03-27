import java.util.ArrayList;
import java.util.Scanner;

// Calculator class that gets input in a loop and prints out the output
// Supports add, sub, mul, div, %, pow, and ()
// Uses RPN (Reverse Polish Notation)
public class Calculator {
	// Output and operator stacks
	public ArrayList<Token> output;
	public ArrayList<Operator> ops;
	public int res;

	// Constructor
	public Calculator() {
		this.output = new ArrayList<Token>();
		this.ops    = new ArrayList<Operator>();
	}

	// Push on `output`
	public boolean push(Token t) {
		return output.add(t);
	}

	public Token pop() {
		if (output.size() < 1) {
			return null;
		}

		return output.remove(output.size() - 1);
	}

	// Peek at `output`
	public Token peek() {
		if (output.size() < 1) {
			return null;
		}

		return output.get(output.size() - 1);
	}

	// Push on `ops`
	public boolean pushOp(Operator op) {
		return ops.add(op);
	}

	// Pop off `ops`
	public Operator popOp() {
		if (ops.size() < 1) {
			return null;
		}

		return ops.remove(ops.size() - 1);
	}

	// Peek at `ops`
	public Operator peekOp() {
		if (ops.size() < 1) {
			return null;
		}

		return ops.get(ops.size() - 1);
	}

	// Order the output stack to RPN
	public int orderRpn(String l) {
		// Loop over each char in the string and order into
		// RPN (Reverse Polish Notation)
		for (int i = 0; i < l.length(); i++) {
			char c = l.charAt(i);
			//System.out.println("Received char " + c);

			switch (c) {
				case ' ':
					// Ignore spaces
					continue;
				case '+':
				case '-':
				case '*':
				case '/':
				case '%':
				case '^':
				case '(':
				case ')':
					// Operator from input
					Operator o1 = Operator.get(c);
					// Operator on operator stack
					Operator o2 = peekOp();

					// Pop from operator stack until operator on operator stack
					// is not precedent
					while (o2 != null && o2 != Operator.OPENP && o2.isPrecedent(o1)) {
						push(new Token(popOp()));
						o2 = peekOp();
					}

					// If operator is not ')', push onto operator stack
					// If operator is ')', pop from operator stack onto the output
					// stack until // there is a '(', then pop the '('
					if (o1 != Operator.CLOSEP) {
						pushOp(o1);
					} else {
						// Pop off operator stack onto output stack
						// until reached '(' or bottom of stack
						while (o2 != null && o2 != Operator.OPENP) {
							push(new Token(popOp()));
							o2 = peekOp();
						}

						// If reached '(' pop off
						// If reached bottom of stack, print error
						if (o2 == Operator.OPENP) {
							popOp();
						} else {
							System.err.println("Mismatched parentheses");
							return -1;
						}
					}
					continue;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					// If input is a number, get start and end index,
					// and iterate over to get the value

					// Store start index of number
					int idx = i;

					// Increment i to end index of number
					while (i + 1 < l.length() && 0x30 <= (c = l.charAt(i + 1)) && c < 0x40) {
						//System.out.println("c: " + c);
						i++;
					}

					// Iterate over number and add each digit
					int val = 0;
					for (int a = idx; a <= i; a++) {
						// Get char at val
						c = l.charAt(a);
						
						// Multiply digit with offset from start index
						val += (c - 0x30) * Math.pow(10, i - a);
						//System.out.println(String.format("%d %d", a, val));
					}
					// Push computed value to output stack
					push(new Token(val));
					continue;
				case 'q':
					// If recieved a 'q' as input, exit the loop
					return 1;
				default:
					// Print error
					System.err.println("Received invalid character");
					return -1;
			}
		}

		// Pop all leftover operators in operator stack to output stack
		while (ops.size() > 0) {
			push(new Token(popOp()));
		}

		return 0;
	}

	// Compute the value when output is ordered in the RPN format
	// with the `orderRpn` method
	public int computeRpn() {
		// Loop through output stack
		for (int i = 0; output.size() > 1; i++) {
			// Get token
			Token t = output.get(i);

			// If token is not an operator and it is the last in the stack,
			// return error, otherwise skip it
			if (!t.is_op) {
				if (i >= output.size() - 1) {
					System.err.println("Missing operators");
					return -1;
				}

				continue;
			}

			Operator op = output.remove(i).op;

			// Error if  a parentheses is the only operator between values
			if (op == Operator.OPENP || op == Operator.CLOSEP) {
				System.err.println("Missing operator");
				return -1;
			}

			// Subtract 2 from `i` for the 2 values and operator
			// combined into one value as a result
			i -= 2;

			// Error if there are less than two values before the operator
			if (i < 0) {
				System.err.println("Ran out of values");
				return -1;
			}

			// Compute the operator with the two previous numbers
			int x = output.remove(i).val;
			int y = output.remove(i).val;
			output.add(i, new Token(op.compute(x, y)));
		}

		// Set the result
		if (output.size() != 1) {
			return -1;
		}

		res = output.get(0).val;

		return 0;
	}

	// Get one line of input and print out result
	// Return val: 0 normal, -1 error, 1 recieved quit
	public int calc() {
		int ret;

		System.out.print("> ");
		Scanner s = new Scanner(System.in);

		// Get a line of input
		String l;
		try {
			l = s.nextLine();
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}

		// Return early if there was an error or quit command
		ret = orderRpn(l);
		if (ret != 0) {
			return ret;
		}

		// Print out the result if there was no error
		return computeRpn();
	}

	// Compute input lines in a loop until 'q' is pressed
	public void run() {
		// If 'q' has been pressed, return
		// Otherwise clear the stacks and run again
		int ret;
		while ((ret = calc()) != 1) {
			if (ret == 0) {
				System.out.println(res);
			}

			output.clear();
			ops.clear();
		}

		return;
	}

	private static String test_basic_ops = "1 + 3 * 4 / 2";
	private static String test_parentheses = "(2 + 3) * 4";
	private static String test_mod = "8 % 3 * 3";
	private static String test_power = "2 ^ 3 + 1";

	private static void test(String l, int expected) {
		int ret;
		Calculator calc = new Calculator();

		System.out.println(String.format("Expr: %s", l));

		ret = calc.orderRpn(l);
		if (ret != 0) {
			System.out.println(String.format("Test failed: returned %d\n", ret));
		}

		// Build RPN string from output stack
		String rpn = "[";
		for (Token t : calc.output) {
			rpn = rpn.concat(t + ", ");
		}
		rpn = rpn.substring(0, rpn.length() - 2);
		rpn = rpn.concat("]");
		System.out.println(String.format("RPN: %s", rpn));

		ret = calc.computeRpn();
		if (ret != 0) {
			System.out.println(String.format("Test failed: returned %d\n", ret));
		}

		System.out.println(String.format("Result: %d", calc.res));

		// Print error message if result is not equal to expected value
		if (calc.res != expected) {
			System.out.println(String.format("Test failed: Expr: %s, expected: %d, result: %d", l, expected, calc.res));
		}

		System.out.println();
	}

	public static void main(String[] args) {
		Calculator calc = new Calculator();

		test(test_basic_ops, 7);
		test(test_parentheses, 20);
		test(test_mod, 6);
		test(test_power, 9);

		calc.run();
	}
}

