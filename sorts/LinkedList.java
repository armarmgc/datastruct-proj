import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.time.Instant;

public class LinkedList {
	public Node head;
	public Node tail;

	// Cache of the last node and index queried
	public int prev_idx = -1;
	public Node prev_node = null;

	public int len = 0;

	// Deep copy
	public LinkedList clone() {
		LinkedList list = new LinkedList();

		Node node = head;

		while (node != null) {
			Node node2 = new Node(node);

			if (node != head) {
				node2.prev.next = node2;
			} else {
				list.head = node2;
			}

			if (node != tail) {
				node2.next.prev = node2;
			} else {
				list.tail = node2;
				break;
			}

			node = node.next;
		}

		list.len = len;

		return list;
	}

	// Return sublist from `start` to `end` by reference
	public LinkedList sublist(int start, int end) throws IndexOutOfBoundsException {
		LinkedList list = new LinkedList();

		list.head = this.getNode(start);
		list.tail = this.getNode(end - 1);
		list.len = end - start;

		return list;
	}

	// Get node at index by reference
	public Node getNode(int idx) throws IndexOutOfBoundsException {
		if (idx < 0 || idx >= len) {
			throw new IndexOutOfBoundsException();
		}

		// If previous node is closer to the index, traverse from there
		int offset = idx - prev_idx;

		if (idx == prev_idx) {
			return prev_node;
		}

		if (offset > 0 && offset <= idx) {
			Node node = prev_node;

			for (int i = 0; i < offset; i++) {
				prev_node = prev_node.next;
			}
			prev_idx = idx;

			return prev_node;
		}

		if (offset < 0 && offset * -1 <= idx) {
			Node node = prev_node;

			for (int i = 0; i < offset * -1; i++) {
				prev_node = prev_node.prev;
			}
			prev_idx = idx;

			return prev_node;
		}

		// If start is closer to the index, traverse from start
		Node node = head;

		for (int i = 0; i < idx; i++) {
			node = node.next;
		}

		prev_node = node;
		prev_idx = idx;

		return node;
	}

	// Get value of node at index
	public int get(int idx) throws IndexOutOfBoundsException {
		return getNode(idx).val;
	}

	// Set the value of node at index
	public void set(int idx, int val) throws IndexOutOfBoundsException {
		getNode(idx).val = val;
	}

	// Add node to end
	public void add(Node node) {
		len++;

		if (head != null && tail != null) {
			tail.next = node;
			node.prev = tail;
			node.next = null;
			tail = node;
			return;
		}

		node.prev = null;
		node.next = null;
		head = node;
		tail = node;
	}

	// Add node with specified value to the end
	public void add(int x) {
		add(new Node(x));
	}

	// Delete node at index
	public int del(int idx) throws IndexOutOfBoundsException {
		Node before = getNode(idx);
		Node cur = null;
		Node after = null;

		cur = before.next;

		if (cur == null) {
			throw new IndexOutOfBoundsException();
		}

		after = cur.next;
		before.next = after;
		len--;

		return cur.val;
	}

	// Insert a node with specified value at index
	public void insert(int x, int idx) throws IndexOutOfBoundsException {
		Node n1 = new Node(x);
		Node n2 = getNode(idx);

		if (n2 == tail) {
			n1 = tail;
		}

		n1.prev = n2.prev;
		n1.next = n1;
		n2.prev = n2;
		len++;
	}

	// Move node1 to before node2
	public void move(Node n1, Node n2) {
		if (n1 == n2) {
			return;
		}

		if (n1 == head) {
			head = n1.next;
			head.prev = null;
		} else if (n1 == tail) {
			tail = n1.prev;
			tail.next = null;
		} else {
			n1.prev.next = n1.next;
			n1.next.prev = n1.prev;
		}

		if (n2 == head) {
			head = n1;
			n1.prev = null;
		} else {
			n1.prev = n2.prev;
			n1.prev.next = n1;
		}

		n1.next = n2;
		n2.prev = n1;
	}

	// Swap nodes at specified indices
	public void swap(int idx1, int idx2) throws IndexOutOfBoundsException {
		int tmp = get(idx1);

		set(idx1, get(idx2));
		set(idx2, tmp);
	}

	// Swap the specified nodes
	public void swapNode(Node node, Node other) {
		int tmp = node.val;

		node.val = other.val;
		other.val = tmp;
	}

	// Clear the list
	public void clear() {
		head = null;
		tail = null;
		len = 0;
	}

	public void bubbleSort() {
		boolean ordered;

		do {
			Node node = head;
			ordered = true;
			while (node != null) {
				if (node.next != null && node.val > node.next.val) {
					swapNode(node, node.next);
					ordered = false;
				}
				node = node.next;
			}
		} while (!ordered);
	}

	public void selectSort() {
		Node node = head;

		for (int s = 0; node != null; s++) {
			int min = node.val;
			int idx = s;

			Node n = node.next;
			for (int i = s + 1; n != null; i++) {
				int val = n.val;
				if (val < min) {
					min = val;
					idx = i;
				}
				n = n.next;
			}

			if (idx != s) {
				swap(s, idx);
			}

			node = node.next;
		}
	}

	public void insertSort() {
		if (head.next == null) {
			return;
		}

		Node node = head.next;

		while (node != null) {
			Node n = node;

			while (n.prev != null) {
				if (n.prev.val < node.val) {
					break;
				}
				n = n.prev;
			}

			Node next = node.next;
			move(node, n);

			if (node == tail) {
				return;
			}

			node = next;

		}
	}

	public void mergeSort() {
		LinkedList list2 = this.clone();
		merger(this, list2, len);
	}

	// Perform merge sort on `LinkedList` a with `LinkedList` b
	public void merger(LinkedList a, LinkedList b, int n) {
		if (n <= 1) {
			return;
		}

		int mid = n / 2;
		int len1 = mid;
		int len2 = n - mid;

		b = a.clone();
		LinkedList l = b.sublist(0, len1);
		LinkedList r = b.sublist(mid, mid + len2);

		merger(l, a, len1);
		merger(r, a, len2);

		merge(a, l, r, n);
	}

	// Perform sorted merge on sorted lists `l` and `r`
	public void merge(LinkedList a, LinkedList l, LinkedList r, int n) {
		int j = 0;
		int k = 0;

		for (int i = 0; i < n; i++) {
			if (j < n / 2 && (k >= n - n / 2 || l.get(j) <= r.get(k))) {
				a.set(i, l.get(j));
				j++;
			} else {
				a.set(i, r.get(k));
				k++;
			}
		 }
	}

	// Check for null next and prev nodes for non-head and non-tail nodes
	public static void check(LinkedList list) {
		Node node = list.head;
		int x = 0;

		while (node != null) {
			if (node != list.head && node.prev == null) {
				System.out.println(String.format("Node prev is null, idx %d, len %d", x, list.len));
				System.exit(1);
			}

			if (node != list.tail && node.next == null) {
				System.out.println(String.format("Node next is null, idx %d, len %d", x, list.len));
				System.exit(1);
			}
			x++;
			node = node.next;
		}
	}

	public String toString() {
		String s = "";
		if (head == null) {
			return s;
		}

		Node node = head;
		int x = 0;

		while (node != null) {
			s = s.concat(Integer.toString(node.val) + "\n");

			if (node == tail) {
				System.out.println("tail");
				return s;
			}

			node = node.next;
			x++;
		}

		s = s.trim();

		return s;
	}

	// Get number of cycles
	public static native long rdtsc();

	static {
		System.loadLibrary("rdtsc");
	}

	// Test specified sort by running it against a random generated
	// list with `items` number of items and `count` times
	public static void test(Sort sort, long count, long items) {
		long cycles = 0;

		long min_cycles = Long.MAX_VALUE;
		long max_cycles = Long.MIN_VALUE;

		double time = 0;

		double min_time = Long.MAX_VALUE;
		double max_time = Long.MIN_VALUE;

		String name = "";
		String stats = "";

		final LinkedList list = new LinkedList();

		// Set function to run and name of the sort by the specified enum
		Runnable fn;

		switch (sort) {
			case BUBBLE:
				name = "Bubble";
				fn = () -> list.bubbleSort();
				break;
			case SELECT:
				name = "Select";
				fn = () -> list.selectSort();
				break;
			case INSERT:
				name = "Insert";
				fn = () -> list.insertSort();
				break;
			case MERGE:
				name = "Merge";
				fn = () -> list.mergeSort();
				break;
			default:
				System.out.println("Invalid sort type");
				return;
		}

		// Run tests
		for (int c = 0; c < count; c++) {
			// Generate random numbers
			Rng rng = new Rng();

			for (int a = 0; a < items; a++) {
				list.add(rng.rand_int());
			}

			// Count time and cycles spent on sorting
			double time_start = Instant.now().toEpochMilli();
			long start = rdtsc();

			fn.run();

			long cur_cycles = rdtsc() - start;
			double cur_time = ((double) Instant.now().toEpochMilli() - time_start) / 1000000;
			cycles += cur_cycles;
			time += cur_time;

			stats = stats.concat(String.format("%d %d\n", items, cur_cycles));

			if (cur_cycles < min_cycles) {
				min_cycles = cur_cycles;
			}

			if (cur_cycles > max_cycles) {
				max_cycles = cur_cycles;
			}
	
			if (cur_time < min_time) {
				min_time = cur_time;
			}

			if (cur_time > max_time) {
				max_time = cur_time;
			}
	
			// Check if the list is sorted
			Node node = list.head;

			while (node != list.tail && node.next != null) {
				if (node.val > node.next.val) {
					System.out.println(list);
					System.out.println(String.format("%d %d", node.val, node.next.val));
					System.out.println("Not sorted");
					break;
				}
				node = node.next;
			}

			list.clear();
		}

		// Print statistics for the test
		System.out.println(String.format("[ %-6s %6d items ] [ %f ms | cycles %9d | min/max time %f / %f | min/max cycles %9d / %9d ]", name, items, time / count, cycles / count, min_time, max_time, min_cycles, max_cycles));

		try {
			// Append the statistics to a file
			FileWriter fwriter = new FileWriter(String.format("stats/%s.dat", name), true);
			fwriter.write(stats, 0, stats.length());
			fwriter.flush();

			fwriter.close();
		} catch (Exception e) {
			System.out.println(String.format("IO error: %s", e));
		}
	}

	public static void run_tests(int count, int items) {
		test(Sort.BUBBLE, count, items);
		test(Sort.SELECT, count, items);
		test(Sort.INSERT, count, items);
		test(Sort.MERGE, count, items);
		System.out.println();
	}

	public static void main(String[] args) {
		int count = 0xf;

		run_tests(count, 10);
		run_tests(count, 2500);
		run_tests(count, 5000);
	}
}

