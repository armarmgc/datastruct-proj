public class Node {
	// String value
	public String s;
	// Integer value
	public int i;
	// Is a number
	public boolean nu;
	// Previous node
	public Node p;
	// Next node
	public Node n;

	// Constructor for string node
	public Node(String s) {
		this.s = s;
	}

	// Constructor for int node
	public Node(int i) {
		this.i = i;
		nu = true;
	}

	// Return integer value as string if node is number, else return string value
	public String toString() {
		if(nu){
			return String.valueOf(i);
		}
		return s;
	}

}

