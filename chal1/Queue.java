public class Queue {
	// Head
	private Node h = null;

	// Tail
	private Node t = null;

	// Add on to the tail
	public void add(Node n) {
		// Make sure arg is not null
		if(n != null){
			if(t != null){
				// If there is a head and tail
				// Set node prev to tail and next to null
				// Set tail next to node
				// Set node to tail
				n.p = t;
				n.n = null;
				t.n = n;
				t = n;
			} else if(h != null){
				// If there is only a head
				// Set tail to node, with prev as head and next as null
				// Set head next to tail
				t = n;
				t.p = h;
				h.n = t;
				t.n = null;
			} else {
				// If there are no head and tail
				// Set head to node, with next and prev nodes as null
				h = n;
				h.p = null;
				h.n = null;
			}
		}
	}
	
	// Delete from the head
	public Node del() {
		// Get head as return value
		Node r = h;
		if(h != null && h.n != null){
			// If head has a next node
			// Set head to the next node
			h = h.n;
			h.p = null;
		} else {
			// No more elements in the queue after this
			h = null;
		}
		return r;
	}
	
	// Delete from the tail
	public Node pop() {
		// Get tail as return value
		Node r = t;
		if(t != null && t.p != null){
			// If tail has a prev node
			// Set tail to the prev node
			t = t.p;
			t.n = null;
		} else {
			// No more elements in the queue after this
			t = null;
		}
		return r;
	}

	// Merge this and another queue
	// Return sorted merged queue
	public Queue merge(Queue o) {
		// Initialize queue as return value
		Queue r = new Queue();
		// Get first two nodes
		Node a = del();
		Node b = o.del();
		// Loop while we have deleted a value from the queues
		while(a != null && b != null) {
			// If the node is numerical perform ordering, otherwise error
			if(a.nu && b.nu){
				// Get values
				int x = a.i;
				int y = b.i;
				// Push nodes in the asending order
				if(x < y){
					r.add(new Node(x));
					r.add(new Node(y));
				} else {
					r.add(new Node(y));
					r.add(new Node(x));
				}
				// Get next two nodes
				a = del();
				b = o.del();
			} else {
				ps("not a number\n");
			}
		}
		return r;
	}

	// Return each node separated by a space
	public String toString() {
		String st = "";
		// Initialize node as head
		Node n = h;
		// Loop through next nodes
		while(n != null){
			// Add to string
			st=st.concat(n.toString()+" ");
			n = n.n;
		}
		return st;
	}

	// Print helper functions
	// Print queue
	public static void pq(Queue q) {
		System.out.println(q);
	}
	// Print node
	public static Node pn(Node n) {
		System.out.println(n);
		return n;
	}
	// Print string
	public static void ps(String s) {
		System.out.print(s);
	}
	
	// Chal1 runner
	public static void chal1() {
		ps("-Challenge 1-\n");
		
		// Static data for inputs
		String[] inps = new String[] {"seven", "slimy", "snakes"};

		// Create Queue
		Queue q = new Queue();
		// Add inputs to queue
		for (String inp : inps) {
			q.add(new Node(inp));
			pq(q);
		}

		// Delete inputs frmo queue
		for (String inp : inps) {
			q.del();
			pq(q);
		}
	}
	
	// Chal2 runner
	public static void chal2() {
		ps("\n-Challenge 2-\n");

		// Static data for 2 lists
		int[] s1 = new int[] {1, 4, 5, 8};
		int[] s2 = new int[] {2, 3, 6, 7};

		// Initialize first queue
		Queue q = new Queue();
		for (int x : s1) {
			q.add(new Node(x));
		}

		// Initialize second queue
		Queue q1 = new Queue();
		for (int x : s2) {
			q1.add(new Node(x));
		}

		// Print lists
		ps("List 1 | ");
		pq(q);
		ps("List 2 | ");
		pq(q1);

		// Merge lists
		ps("Merged | ");
		pq(q.merge(q1));
	}
	
	// Chal3 runner
	public static void chal3() {
		ps("\n-Challenge 3-\n");

		// Static data for inputs
		int[] inps = new int[] {1, 2, 3};

		// Initialize queue
		Queue q = new Queue();
		for (int inp : inps) {
			q.add(new Node(inp));
		}
		pq(q);

		// Pop items in queue and add them to other queue
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

