public class Node {
	public Node prev;
	public Node next;
	public int val;

	public Node(int val) {
		this.val = val;
	}

	public Node(Node prev, Node next, int val) {
		this.prev = prev;
		this.next = next;
		this.val = val;
	}

	public Node(Node n) {
		this.prev = n.prev;
		this.next = n.next;
		this.val = n.val;
	}
}

