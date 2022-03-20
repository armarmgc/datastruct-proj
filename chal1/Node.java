public class Node {
	public String s;
	public int i;
	public boolean nu;
	public Node p;
	public Node n;

	public Node(String s) {
		this.s = s;
	}

	public Node(int i) {
		this.i = i;
		nu = true;
	}

	public Node clone() {
		if(nu){
			return new Node(i);
		} else {
			return new Node(s);
		}
	}

	public void setn(Node o) {
		n = o.clone();
	}

	public void setp(Node o) {
		p = o.clone();
	}

	public String toString() {
		if(nu){
			return String.valueOf(i);
		}
		return s;
	}

}

