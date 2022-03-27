---
layout: home
title: Challenge 2 Learnings
permalink: /learnings/chal2
---

# Table of Contents
- [Design](#design)
- [Learnings](#learnings)
	- [Parsing Strings](#parsing-strings)
	- [Managing Stacks](#managing-stacks)

Challenge 2 was to make a Calculator that used [Reverse Polish Notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation).

```java
$ java Calculator
Expr: 1 + 3 * 4 / 2
RPN: [1, 3, 4, *, 2, /, +]
Result: 7

Expr: (2 + 3) * 4
RPN: [2, 3, +, 4, *]
Result: 20

Expr: 8 % 3 * 3
RPN: [8, 3, %, 3, *]
Result: 6

Expr: 2 ^ 3 + 1
RPN: [2, 3, ^, 1, +]
Result: 9

> 1 + 1
2
>
```

## Design
The calculator first runs tests, and then provides a prompt to evaluate expressions in a loop.

```java
private static void test(String l, int expected) {
...
}

public static void main(String[] args) {
	Calculator calc = new Calculator();

	test(test_basic_ops, 7);
	test(test_parentheses, 20);
	test(test_mod, 6);
	test(test_power, 9);

	calc.run();
}
```

The test function calculates the expression, and then compares it to the expected value. If the expression does not calculate to be the expected value, an error is printed.

After the tests are checked, the run function is called, which starts the prompt. The `run` function calls the `calc` function in a loop.

The calc function returns 1 to exit the loop, -1 for error, and 0 for normal execution.
If the function returned 1, we return.
If the function retruned 0, we print out the computed result.
If the function returned -1 or 0, we clear the output and operation stacks and compute the next expression.

```java
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
```

Each line recieved are parsed by iterating over each character to put the expression into RPN.

```java
public int orderRpn(String l) {
	// Loop over each char in the string and order into
	// RPN (Reverse Polish Notation)
	for (int i = 0; i < l.length(); i++) {
		char c = l.charAt(i);

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
				// Operator
				...
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
				// Number
				...
			case 'q':
				// If recieved a 'q' as input, exit the loop
				return 1;
			default:
				// Print error
				System.err.println("Received invalid character");
				return -1;
```

Then the expression is then calculated by evaluating the RPN expression.

```java
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
```

## Learnings

### Parsing strings
In this calculator, we had to parse lines of input and differentiate between operators and numbers.

### Managing stacks
There were an output and operator stack to order into RPN, in which I used push, pop, and peek operations.

```java
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
```

