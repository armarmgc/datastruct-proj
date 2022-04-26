---
layout: page
title: Practice MCQ 1 Corrections
permalink: /ap/mcq1
---

# Table of Contents
- [Q5 Compound Boolean expression with x and y](#q5-compound-boolean-expression-with-x-and-y)
- [Q13 mystery method with pairwise traversal of array](#q13-mystery-method-with-pairwise-traversal-of-array)
- [Q16 calculate method with 2D int array parameter](#q16-calculate-method-with-2d-int-array-parameter)
- [Q25 incrementing count in nested loops](#q25-incrementing-count-in-nested-loops)
- [Q26 Methods start and changeIt with aliases](#q26-methods-start-and-changeit-with-aliases)
- [Q28 Sorting 1D int array statement count](#q28-sorting-1d-int-array-statement-count)
- [Q30 Price of ink pens using getCost method](#q30-price-of-ink-pens-using-getcost-method)
- [Q35 Iterative binarySearch of 1D int array](#q35-iterative-binarysearch-of-1d-int-array)
- [Q37 concatWords method with String array](#q37-concatwords-method-with-string-array)
- [Q39 Consider the following code segment. Wh...](#q39-consider-the-following-code-segment-wh)

## Q5 Compound Boolean expression with x and y

```java
(x || y) && x
```

Which of the following always evaluates to the same value as the expression above?

```
A) x      (Correct Answer)
B) y
C) x && y (Wrong Answer)
D) x || y
```

Since x OR y is a superset of x, this means that (x OR y) AND x will be x because AND is only one when both of these are one.

I'm not sure why I chose C on this one.

## Q13 mystery method with pairwise traversal of array

```java
private int[] numbers;

public void mystery (int x)
{
  for (int k =  1; k < numbers.length; k = k + x)
  {
    numbers[k] = numbers[k - 1] + x;
  }
}
```

Assume that numbers has been initialized with the following values.

{17, 34, 21, 42, 15, 69, 48, 25, 39}

Which of the following represents the order of the values in numbers as a result of the call mystery(3)?

```
A) {17, 20, 21, 42, 45, 69, 48, 51, 39} (Correct Answer)
B) {17, 20, 23, 26, 29, 32, 35, 38, 41} (Wrong Answer)
C) {17, 37, 21, 42, 18, 69, 48, 28, 39}
D) {20, 23, 21, 42, 45, 69, 51, 54, 39}
E) {20, 34, 21, 45, 15, 69, 51, 25, 39} 
```

Since mystery is passed in 3, the loop starts at the first index and assigns every third value after the previous value plus 3.

I chose B because I forget about the increment by x, so I answered as if every value starting at index one is assigned the previous value.

## Q16 calculate method with 2D int array parameter

```java
public static int calculate(int[][] values)
{
  int found = values[0][0];
  int result = 0;
  for (int[] row : values)
  {
    for (int y = 0; y < row.length; y++)
    {
      if (row[y] > found)
      {
        found = row[y];
        result = y;
      }
    }
  }
}
```

Which of the following best describes what is returned by the calculate method?

```
A) The largest value in the two-dimensional array
B) The smallest value in the two-dimensional array
C) The row index of an element with the largest value in the two-dimensional array (Wrong Answer)
D) The row index of an element with the smallest value in the two-dimensional array
E) The column index of an element with the largest value in the two-dimensional array (Correct Answer)
```

The function is iterating over each row in the values and returns the `y` value, which is being used to index the row. This means that the `y` value represents the column.

I missed this and went too fast on this problem and chose rows instead of columns.

## Q25 Incrementing count in nested loops

```java
int count = 0;

for (int x = 0; x < 4; c++)
{
  for (int y = x; y < 4; y++)
  {
    count++;
  }
}
System.out.println(count);
```

What is printed as a result of executing the code segment?

```
A) 4
B) 8
C) 10 (Correct Answer)
D) 16 (Wrong Answer)
E) 20
```

There are two nested for loops, the outer one just iterates 4 times and the inner one iterates for 4 - x times.

This means that the amount of times iterated will be 1 + 2 + 3 + 4 = 10.

I glanced over the fact that the inner loop was initialized to x instead of 0, so I did 4 * 4 = 16 instead.

## Q26 Methods start and changeIt with aliases

```java
public static void changeIt(int[] arr, int val, String word)
{
  arr = new int[5];
  val = 0;
  word = word.substring(0, 5);

  for (int k = 0; k < arr.length; k++)
  {
    arr[k] = 0;
  }
}

public static void start()
{
  int[] nums = {1, 2, 3, 4, 5};
  int value = 6;
  String name = "blackboard";

  changeIt(nums, value, name);

  for (int k = 0; k < nums.length; k++)
  {
    System.out.print(nums[k] + " ");
  }

  System.out.print(value + " ");
  System.out.print(name);
}
```

What is printed as a result of the call start()?

```
A) 0 0 0 0 0 0 black
B) 0 0 0 0 0 6 blackboard
C) 1 2 3 4 5 6 black (Wrong Answer)
D) 1 2 3 4 5 0 black
E) 1 2 3 4 5 6 blackboard (Correct Answer)
```

The changeIt method takes in an array, int, and string. The int and string are passed in as value, but the array is a reference. However, the array is reinitialized to a different memory location.

I was confused on pass by reference and didn't understand about how the array reference is just passed by value and changing that doesn't change the other reference location.

## Q28 Sorting 1D int array statement count

```java
public static void sort(int[] data)
{
  for (int j = 0; j < data.length - 1; j++)
  {
    int m = j;
    for (int k = j + 1; k < data.length; k++)
    {
      if (data[k] < data[m])
      {
        m = k;
      }
    }
    int temp = data[m];
    data[m] = data[j];
    data[j] = temp;
  }
}
```

Assume that sort is called with the array {1, 2, 3, 4, 5, 6}. How many times will the expression indicated by /* Compare values */ and the statement indicated by /* Assign to temp */ execute?

```
A) Compare values / Assign to temp (Wrong Answer)
   15 / 0
B) Compare values / Assign to temp (Correct Answer)
   15 / 5
C) Compare values / Assign to temp
   15 / 6
D) Compare values / Assign to temp
   21 / 5
E) Compare values / Assign to temp
   21 / 6
```

The sort method loops through all but the last element in the array. Compare is run `length - index` times and assigning temp is run once per iteration. Since the index goes through values 0, 1, 2, 3, 4, 5, Compare is run 5 + 4 + 3 + 2 + 1 + 0 = 15 and assiging temp occurs 5 times.

I think I misclicked on this question, I'm not sure.

## Q30 Price of ink pens using getCost method

```
        Number of Boxes          Price per Box
+------------------------------+---------------+
| 1 up to but not including 5  |     $5.00     |
| 5 up to but not including 10 |     $3.00     |
|         10 or more           |     $1.50     |
+------------------------------+---------------+
```

The following incomplete method is intended to return the total cost of an order based on the value of the parameter numBoxes.

```java
public static double getCost(int numBoxes)
{
  double totalCost = 0.0;

  /* missing code */

  return totalCost;
}
```

Which of the following code segments can be used to replace /* missing code */ so that method getCost will work as intended?

I.
```
if (numBoxes >= 10)
     {
       totalCost = numBoxes * 1.50;
     }
     if (numBoxes >= 5)
     {
       totalCost = numBoxes * 3.00;
     }
     if (numBoxes > 0)
     {
       totalCost = numBoxes * 5.00;
     }
 ```

II.
```
if (numBoxes >= 10)
     {
       totalCost = numBoxes * 1.50;
     }
     else if (numBoxes >= 5)
     {
       totalCost = numBoxes * 3.00;
     }
     else
     {
       totalCost = numBoxes * 5.00;
     }
```

III.
```
if (numBoxes > 0)
     {
       totalCost = numBoxes * 5.00;
     }
     else if (numBoxes >= 5)
     {
       totalCost = numBoxes * 3.00;
     }
     else if (numBoxes >= 10)
     {
       totalCost = numBoxes * 1.50;
     }
```

A) I only
B) II only (Correct Answer)
C) III only
D) I and II (Wrong Answer)
E) II and III

The first one is incorrect because the if statements do not have conditions that for both sides and they are not else statements.

The second one contains else statements, so is correct as the previous conditions result in the correct multiplier.

The third one is also incorrect work because the order of the if and else conditions results in incorrect multipliers selected.

I think I chose I and II because I thought I had else or returned or something.

## Q35 Iterative binarySearch of 1D int array

```java
public static int binarySearch(int[] data, int target)
{
  int start = 0;
  int end = data.length - 1;
  while (start <= end)
  {
    int mid = (start+ end / 2; /* Calculate midpoint */
    if (target < data[mid])
    {
      end = mid - 1;
    }
    else if (target > data[mid])
    {
      start = mid + 1;
    }
    else
    {
      return mid
    }
  }
  return -1;
}
```

Consider the following code segment.

`int[] values = {1, 2, 3, 4, 5, 8, 8, 8}; int target = 8;`

What value is returned by the call binarySearch(values, target)?

A) -1
B) 3
C) 5 (Correct Answer)
D) 6 (Wrong Answer)
E) 8

The binary search just gets the midpoint by dividing the `end - start` by 2 rounded down, end starting at the end index (length - 1). So `7 / 2 = 3`. Three is then used as the new start because 8 is above the selected 4 value.The index is calculated `(7 - 3) / 2 = 5`, which is an 8.

I went through the binary search as if the end started at length instead of the end index.

## Q37 concatWords method with String array

```java
public static String concatWords(String[] words, int startIndex)
{
  String result = "";

  /* missing code */

  return result;
}
```

For example, the following code segment uses a call to the concatWords method.

```java
String[] things = {"Bear", "Apple", "Gorilla", "House', "Car"};
System.out.println(concatWords(things, 2));
```

When the code segment is executed, the string "CarHouseGorilla" is printed.

The following three code segments have been proposed as replacements for / * missing code * /.

I.
```java
for (int k = startIndex; k < words.length; k++)
{
  result += words[k] + words[words.length - k - 1];
}
```

II.
```java
int k = words.length - 1;
while (k >= startIndex)
{
  result += words[k];
  k--;
}o
```

III.
```java
String[] temp = new String[words.length];
for (int k = 0; k <= words.length / 2; k++)
{
  temp[k] = words[words.length - k - 1];
  temp[words.length - k - 1] = words[k];
}

for (int k = 0; k < temp.length - startIndex; k++) {
    result += temp[k];
}
```

Which of these code segments can be used to replace /* missing code */ so that concatWords will work as intended?

A) I only
B) II only (Wrong Answer)
C) III only
D) I and II
E) II and III (Correct Answer)

THe first one doesn't work, it iterates from the startIndex and length from both directions and adds both strings, doesn't really make any sense.

The second one iterates from the end to start index, adding each string to the result, so is correct.

The third one first reverses the string into a temporary array, and then iterates length - startIndex times over that array, so this works as well.

I was confused on what III did, so I just chose II only.

## Q39 Consider the following code segment. Wh...

```java
List<String> students = new ArrayList<String>();

students.add("Alex");
studnets.add("Bob");
students.add("Carl");

for (int k = 0; k < students.size(); k++)
{
  System.out.print(students.set(k, "Alex") " ");
}

System.out.println();

for (String str : students)
{
  System.out.print(str + " ");
{
```

What is printed as a result of executing the code segment?

```
A) Alex Alex Alex (Wrong Answer)
   Alex Alex Alex

B) Alex Alex Alex
   Alex Bob Carl

C) Alex Bob Carl (Correct Answer)
   Alex Alex Alex

D) Alex Bob Carl
   Alex Bob Carl

E) Nothing is printed because the first print statement will cause a runtime exception to be thrown.
```

Since the set method returns the value that was replaced, the program will first print the values of the array had previously, and then print out the values the array was set afterwards.

I didn't know what set returned, so I guessed that it returned the value that was being set, but that doesn't really make sense because you already know that value as it is passed in as an argument to the set method.

