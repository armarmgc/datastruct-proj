---
layout: page
title: Clock
permalink: /analysis/clock
---

# Table of Contents
- [Overview](#overview)
- [Design](#design)
    - [Running the clock](#running-the-clock)
    - [String representation of digits](#string-representation-of-digits)
    - [Formatting the time](#formatting-the-time)

# Overview
This program was a contribution I made to another person's [project][].

[project]: https://github.com/chenxin-chex/Chenxin-Individual-Tri-3

This program was a stopwatch made using printable characters.

```
 ---------                 ---------   ---------                 --------- 
|         |           |             |           |   |         | |          
|         |           | :           |           | : |         | |          
|         |           |             |           |   |         | |          
                           ---------   ---------     ---------   --------- 
|         |           |   |                     |             |           |
|         |           | : |                     | :           |           |
|         |           |   |                     |             |           |
 ---------                 ---------   ---------                 --------- 

Press 'q' to quit
>
```

The `Clock` class stores the time as a single `int` and has an increment method.

```java
public class Clock {
  private int time;

  public Clock() {
    this.time = 0;
  }

  public void increment() {
    time++;
  }
```


# Design

## Running the clock
The main function first initializes the `Clock`, then starts a new thread that manages the clock in a loop:

- Return if the clock is not running.
- Print the [form feed][] character to clear the screen.
- Print the clock in the format `hh::mm::ss`.
- Print a prompt for directions to stop the clock.

[form feed]: https://en.wikipedia.org/wiki/Page_break

Input is then recieved in a loop and the clock is stopped and the program returns if `q` is recieved.

```java
  public static void main(String[] args) {
    Clock clock = new Clock();

    new Thread(() -> {
      for (;;) {
        if (clock.isRunning()) {
          return;
        }                         

        // Clear the screen
        System.out.println("\033c");

        // Print out the clock
        System.out.println(clock);

        System.out.println("Press 'q' to quit");
        System.out.print("> ");

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.err.println(e);
        }

        clock.increment();
      }
    }).start();

    // If 'q' is entered, quit the clock
    Scanner stdin = new Scanner(System.in);

    for (;;) {
      while (stdin.nextLine().equals("q")) {
        clock.stop();

        return;
      }
    }
  }
```

## String representation of digits

This clock displays each digit with 7 segments.

```
        1       
    ---------  
   |         |  
 0 |         | 2
   |    4    |  
    ---------   
   |         |  
 3 |         | 5
   |    6    |  
    ---------   
```

Each digit is represented with an array of booleans that specify whether each segment is on or off.

I decided to use boolean expressions to get the states of each segment, though hardcoding the segments was also an option.

To create boolean expressions for each segment, a table can be created with on and off states corresponding to the digits in binary.

Showing the numbers in binary is necessary for optimizing the boolean expressions.

```
bits         segment
--------------------------
ABCD | 0  1  2  3  4  5  6
--------------------------
0000 | 1, 1, 1, 1, 0, 1, 1
0001 | 0, 0, 1, 0, 1, 1, 0
0010 | 0, 1, 1, 1, 0, 0, 1
0011 | 0, 1, 1, 0, 1, 1, 1
0100 | 1, 0, 1, 0, 1, 1, 0
0101 | 1, 1, 0, 0, 1, 1, 1
0110 | 1, 1, 0, 1, 1, 1, 1
0111 | 0, 1, 1, 0, 0, 1, 0
1000 | 1, 1, 1, 1, 1, 1, 1
1001 | 1, 1, 1, 0, 1, 1, 0
```

With this you can create the unoptimized canonical form of a boolean expression for the first segment (index 0):

```
   0        4         5          6         8         9
0b0000 || 0b0100 || 0b0101 || 0b0110 || 0b1000 || 0b1001
```

There are several methods that can be used to optimize these boolean expressions. I used a [Karnaugh Map][] to do this. The maps created are shown in the [clock_layout][] file in the source.

[Karnaugh Map]: https://en.wikipedia.org/wiki/Karnaugh_map
[clock_layout]: https://github.com/chenxin-chex/Chenxin-Individual-Tri-3/blob/main/clock_layout

These boolean expressions calculate the on and off states of each segment and return then in an array of booleans in `getSegments`.

```java
  // Return the segments for a specific digit
  // Work done in `clock_layout`
  public boolean[] getSegments(int digit) {
    boolean[] segments = new boolean[7];

    segments[0] = (digit & 0b0111) == 0b0000 ||
                  (digit & 0b1101) == 0b0100 ||
                  (digit & 0b1111) == 0b1001 ||
                  (digit & 0b1111) == 0b0101;

    segments[1] = (digit & 0b1010) == 0b0010 ||
                  (digit & 0b0111) == 0b0000 ||
                  (digit & 0b1111) == 0b1001 ||
                  (digit & 0b1111) == 0b0101;

    segments[2] = (digit & 0b1100) == 0b0000 ||
                  (digit & 0b1110) == 0b1000 ||
                  (digit & 0b1111) == 0b0100 ||
                  (digit & 0b1111) == 0b0111;

    segments[3] = (digit & 0b0111) == 0b0000 ||
                  (digit & 0b1011) == 0b0010;

    segments[4] = (digit & 0b1110) == 0b0010 ||
                  (digit & 0b1110) == 0b1000 ||
                  (digit & 0b1101) == 0b0100 ||
                  (digit & 0b1111) == 0b0101;

    segments[5] = (digit & 0b1100) == 0b0100 ||
                  (digit & 0b0111) == 0b0000 ||
                  (digit & 0b0111) == 0b0001 ||
                  (digit & 0b1111) == 0b0011;

    segments[6] = (digit & 0b0111) == 0b0000 ||
                  (digit & 0b1011) == 0b0010 ||
                  (digit & 0b1111) == 0b0011 ||
                  (digit & 0b1111) == 0b0101;

    return segments;
  }
```

Using these segments, the string representation of a digit is found by only showing the segment if the corresponding boolean in the array is set.

```java
  // Get the string representation of a digit as a string
  public String getSegmentsString(int digit) {
    boolean[] segments = getSegments(digit);
    String[] strings = new String[7];

    String bar = "---------";

    strings[0] = segments[0] ? "|" : " ";
    strings[1] = segments[1] ? "---------" : "         ";
    strings[2] = segments[2] ? "|" : " ";
    strings[3] = segments[3] ? "|" : " ";
    strings[4] = segments[4] ? "---------" : "         ";
    strings[5] = segments[5] ? "|" : " ";
    strings[6] = segments[6] ? "---------" : "         ";

    return String.format(" %s \n", strings[1]) +
           String.format("%s         %s\n", strings[0], strings[2]) +
           String.format("%s         %s\n", strings[0], strings[2]) +
           String.format("%s         %s\n", strings[0], strings[2]) +
           String.format(" %s \n", strings[4]) +
           String.format("%s         %s\n", strings[3], strings[5]) +
           String.format("%s         %s\n", strings[3], strings[5]) +
           String.format("%s         %s\n", strings[3], strings[5]) +
           String.format(" %s ", strings[6]);
  }
```

To get the string representation of a multidigit number, each digit from right to left is calculated and prepended to the resulting string.

```java
  // Print a multidigit number as a string
  public String getNumberString(int time, int num_digits) {
    int tmp = time;
    String s = "";

    for (int i = 0; i < num_digits; i++) {
      s = combineStrings(getSegmentsString(tmp % 10, s);

      tmp /= 10;
    }

    return s;
  }
```

## Formatting the time
The time is split into the number of hours, minutes, and seconds.

Then the string representation of these numbers are concatenated between an ascii colon.
    
```java
  // String representation of a colon that separates the hours/minutes/seconds
  private static String colon = " \n \n:\n \n \n \n:\n \n ";

  // Print the clock's time in hh::mm::ss format
  public String toString() {
    int hours = this.time / 3600;
    int minutes = (this.time / 60) % 60;
    int seconds = this.time % 60;

    String s = "";
    s = combineStrings(s, getNumberString(hours,   2));
    s = combineStrings(s, colon);
    s = combineStrings(s, getNumberString(minutes, 2));
    s = combineStrings(s, colon);
    s = combineStrings(s, getNumberString(seconds, 2));

    return s;
  }
```

