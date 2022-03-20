public class Queue {
	private Node h = null;
	private Node t = null;

	public void add(Node n) {
		if(n != null){
			if(t != null){
				t.n = n;
				n.p = t;
				n.n = null;
				t = n;
			} else if(h != null){
				t = n;
				t.p = h;
				h.n = t;
				t.n = null;
			} else {
				h = n;
				h.p = null;
				h.n = null;
			}
		}
	}
	
	public Node del() {
		Node r = h;
		if(h != null && h.n != null){
			h = h.n;
			h.p = null;
		} else {
			h = null;
		}
		return r;
	}
	
	public Node pop() {
		Node r = t;
		if(t != null && t.p != null){
			t = t.p;
			t.n = null;
		} else {
			t = null;
		}
		return r;
	}

	public Queue merge(Queue o) {
		Queue r = new Queue();
		Node a = del();
		Node b = o.del();
		while(a != null && b != null) {
			if(a.nu && b.nu){
				int x = a.i;
				int y = b.i;
				if(x < y){
					r.add(new Node(x));
					r.add(new Node(y));
				} else {
					r.add(new Node(y));
					r.add(new Node(x));
				}
				a = del();
				b = o.del();
			} else {
				ps("not a number\n");
			}
		}
		return r;
	}

	public String toString() {
		String st = "";
		Node n = h;
		while(n != null){
			st=st.concat(n.toString()+" ");
			n = n.n;
		}
		return st;
	}

	public static void pq(Queue q) {
		System.out.println(q);
	}

	public static Node pn(Node n) {
		System.out.println(n);
		return n;
	}

	public static void ps(String s) {
		System.out.print(s);
	}
	
	public static void chal1() {
		ps("-Challenge 1-\n");
		
		String[] inps = new String[] {"seven", "slimy", "snakes"};

		Queue q = new Queue();
		for (String inp : inps) {
			q.add(new Node(inp));
			pq(q);
		}

		for (String inp : inps) {
			q.del();
			pq(q);
		}
	}
	
	public static void chal2() {
		ps("\n-Challenge 2-\n");

		int[] s1 = new int[] {1, 4, 5, 8};
		int[] s2 = new int[] {2, 3, 6, 7};

		Queue q = new Queue();
		for (int x : s1) {
			q.add(new Node(x));
		}

		Queue q1 = new Queue();
		for (int x : s2) {
			q1.add(new Node(x));
		}

		ps("List 1 | ");
		pq(q);
		ps("List 2 | ");
		pq(q1);

		ps("Merged | ");
		pq(q.merge(q1));
	}
	
	public static void chal3() {
		ps("\n-Challenge 3-\n");
		int[] inps = new int[] {1, 2, 3};

		Queue q = new Queue();
		for (int inp : inps) {
			q.add(new Node(inp));
		}
		pq(q);

		Queue s = new Queue();
		for (int inp : inps) {
			s.add(q.pop());
		}

		pq(s);
	}

	public static void main(String[] args) {
		chal1();
		chal2();
		chal3();
	}
}

