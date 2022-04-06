---
layout: page
title: Sorts Analysis
permalink: /analysis/sorts
---

# Table of Contents
- [Overview](#overview)
- [Design](#design)
	- [Generating random lists](#generating-random-lists)
	- [Measuring clock cycles](#measuring-clock-cycles)
	- [Caching previous node](#caching-previous-node)
- [Conclusions](#conclusions)

# Overview
This project is a program with sorting methods of a custom made data structure and records statistics.

Tester functions are used to easily test the sorts by randomly generating a list to be sorted based on arguments.

This function takes in the sort method, number of repetitions, and the number of items in the list that is tested.

A `Runnable` is assigned based on the sort specified, as well as the name that is used for recording statistics.

```java
// Test specified sort by running it against a random generated
// list with `items` number of items and `count` times
public static void test(Sort sort, long count, long items) {
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
```

For each iteration, a random list is generated according to the arguments.

```java
	// Run tests
	for (int c = 0; c < count; c++) {
		// Generate random numbers
		Rng rng = new Rng();

		for (int a = 0; a < items; a++) {
			list.add(rng.rand_int());
		}
```

Next the sort is run and the time in milliseconds and clock cycles taken to run is recorded.
The min and max time and cycles are also set if appropriate.

```java
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
```

The list is checked to ensure it is sorted.

```java
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
```

The statistics are printed and appended to in a file.

Each sort has a different `.dat` file in the `stats/` dir.

```java
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
```

# Design

## Generating random lists
Numbers are generated using [xorshift](https://en.wikipedia.org/wiki/Xorshift), which is a very fast method of generating pseudorandom numbers.

```java
public class Rng {
	public int seed;

	public Rng() {
		seed = Instant.now().getNano();
	}

	public Rng(int seed) {
		this.seed = seed;
	}

	// xorshift
	public int rand_int() {
		seed ^= seed << 13;
		seed ^= seed >> 17;
		seed ^= seed << 5;
		return seed;
	}
```

The following graph shows the clock cycles of xorshift and the java `Random` class.

Xorshift is averages around about 30 cycles, and java `Random` about 40 cycles.

The graph shows that xorshift is about 30% faster.

![Graph comparing xorshift and java Random](/datastruct-proj/assets/images/xorshift_vs_javarand.png)

## Measuring clock cycles
The measurements not only include time, but also include clock cycles.

This is because clock cycles are a measure of performance that is consistent between machines.

### Processor clock overview
The clock is a circuit inside the processor that oscillates from on and off states.

The clock synchronizes the operations that the processor runs, so operations start when the clock changes state.

This means the higher the clock frequency, the more instructions will be executed, although the instructions per clock cycle will remain a similar amount.

Even if the processor runs at a faster clock rate, the number of clock cycles needed to run a program should remain nearly the same.
This allows for consistency between different machines.

### Importing a C function into Java with JNI
Since java doesn't have a method to measure clock cycles, this project imports a function from C called `rdtsc`. This function gets the current number of clock cycles since the system's last reset.

A Java Native Interface (JNI) header file must be created for the importing class for native methods.

The C code can then be compiled as a [shared library][] (linux) or [dll][] (windows) with specific naming: `librdtsc.so` for linux and `rdtsc.dll` for windows.

[shared library]: https://tldp.org/HOWTO/Program-Library-HOWTO/shared-libraries.html
[dll]: https://tldp.org/HOWTO/Program-Library-HOWTO/shared-libraries.html

I won't go in depth on this subject as it is a bit off topic, but the source is on [github][] with the `run` file having the commands used to compile the libraries and header files.

[github]: https://github.com/armarmgc/datastruct-proj/tree/master/sorts

### Native methods in Java
To import the function from C, we have to first declare a native method. The native keyword is used to specify a method that is imported form another language.

Then, we use `System.loadLibrary` to load the method from a library file.

```java
// Get number of cycles
public static native long rdtsc();

static {
    System.loadLibrary("rdtsc");
}
```

Now we can use this function to get the elapsed number of cycles for the sort.

```java
long start = rdtsc();

fn.run();

long cur_cycles = rdtsc() - start;
```

## Caching previous node
My linked list implementation caches the previous node in order to speed up queries that are close to each other.

If the previous node that was queried is closer to the current queried node, the node traverses from there instead of starting at the head of the list.

```java
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
```

If the head of the list is closer, just traverse normally from the head.

```java
	// If start is closer to the index, traverse from start
	Node node = head;

	for (int i = 0; i < idx; i++) {
		node = node.next;
	}

	prev_node = node;
	prev_idx = idx;

	return node;
}
```

This allows consecutive accesses of the entire list to be O(1), and since this type of access is the most common, it improves the main disadvantage of a linked list of slow query times that are typically O(n).

# Conclusions
The following graphs shows the number of clock cycles and time that each sort use.

<pre>
--- Key ---
Purple - Bubble
Green  - Select
Blue   - Insert
Orange - Merge
</pre>

- clock cycles


![Compare clock cycles of sorts](/datastruct-proj/assets/images/compare_sorts_cycles.png)


- time


![Compare clock cycles of sorts](/datastruct-proj/assets/images/compare_sorts_time.png)

You can see that `merge` sort is the fastest and is very consistent in its time taken, as it has an O(n log(n)) time complexity in worst and best cases. The best and worst case time complexity being the same means that there will be a very low amount of variance.


In contrast `bubble`, `select`, and `insert` sorts increase exponentially, as their worst and average cases are O(n^2). `Select` sort has a best case of O(n^2), while `bubble` and `insert` sort has a worse case of O(n), so `bubble` and `insert` sort increase at a slower rate.

Order of efficiency:
1. Merge
2. Insert
3. Bubble
4. Select

