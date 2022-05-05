---
layout: page
title: Practice MCQ 2 Corrections
permalink: /ap/mcq2
---

# Table of Contents
- [MCQ Score](#mcq-score)
- [Q4 print X Y Z](#q4-print-x-y-z)
- [Q7 if and compound boolean equivalence](#q7-if-and-compound-boolean-equivalence)
- [Q11 code to print 987654321](#q11-code-to-print-987654321)
- [Q31 count negative values](#q31-count-negative-values)
- [Q33 array with A through L](#q33-array-with-a-through-l)

## MCQ Score

Score: 35/40
![MCQ Score 35/40](/datastruct-proj/assets/images/mcq_2_score.png)

## Q4 print X Y Z

```java
public static void message(int a, int b, int c)
{
    if (a < 10)
    {
        if (b < 10)
        {
            System.out.print("X");
        }
        System.out.print("Y");
    }
    if (c < 10)
    {
        if (b > 10)
        {
            System.out.print("Y");
        }
        else
        {
            System.out.print("Z");

        }
    }
}
```

What is printed as a result of the call `message(5, 15, 5)`?

```
A) XY
B) XYZ
C) Y (Wrong Answer)
D) YY (Correct Answer)
E) Z
```

This problem is just traversing the logic of nested ifs.

The first `if` case runs because `a` is less than 10. Since `b` is not less than 10, `X` is not printed, but `Y` is unconditionally printed.

The second if runs because `c` is less than 10. A second `Y` is printed because `b` is greater than 10, so the correct answer is `YY`: choice D.

I glanced over the unconditional print of `Y` in the first if statement, so I thought only a single `Y` was going to be printed and chose C.

## Q7 If and compound boolean equivalence

```java
int num = /* initial value not shown */;
boolean b1 = true;
if (num > 0)
{
    if (num >= 100)
    {
        b1 = false;
    }
}
else
{
    if (num >= -100)
    {
        b1 = false;
    }
}
```

Which of the following statments assign the same value ot `b2` as the code segment assigns to `b1` for all avlues of `num`?

```
A) boolean b2 = (num > -100) && (num < 100); (Wrong Answer)
B) boolean b2 = (num > -100) || (num < 100);
C) boolean b2 = (num < -100) || (num > 100);
D) boolean b2 = (num < -100) && (num > 0 || num < 100);
E) boolean b2 = (num < -100) || (num > 0 && num < 100); (Correct Answer)
```

So we need to find the conditions equivalent to the above code segment that can be written in a single line.

First `b1` is initialized to true and is set to false based on some conditions, so those conditions in the code segment will be the inverse of the conditions of the answer.

The outermost if block assigns `b1` to false if it is values that are greater than 0 if it is greater than 100.

The else part checks for negative values and 0 if it is greater than or equal to -100.

So when `b1` is not set to false is when values above 0 are less than 10, or if it is a negative value is below -100, which choice E represents.

I misread the `num >= -100` as `num <= -100` and chose A.

## Q11 code to print 987654321

Which of the following code segments produces the output "987654321"?

A) (Wrong Answer)
```java
int num = 10;
while (num > 0)
{
    System.out.print(num);
    num--;
}
```

B)
```java
int num = 10;
while (num >= 0)
{
    System.out.print(num);
    num--;
}
```

C) (Correct Answer)
```java
int num = 10;
while (num > 1)
{
    num--;
    System.out.print(num);
}
```

D)
```java
int num = 10;
while (num >= 1)
{
    num--;
    System.out.print(num);
}
```

E)
```java
int num = 0;
while (num <= 9)
{
    System.out.print(10 - num);
    num++;
}
```

The code segment that will be correct just iterates from 9 to 1 and prints each number.

So to narrow down our possible solutions, we can check which ones starts out at printing a `9`.

The A and B do not as `num` starts at 10 and is not decremented before the print.

The E also doesn't work because the first value printed is `10`, so the only possible choices are C and D.

The only difference between them are the condition `num > 1` for C and `num >= 1`. Since the `num` is decremented before the print, C is correct because it iterates from 10 to 2, but decrements the number before printing, meaning that it will print 9 to 1.

I probably chose A because I just saw that the condition `num > 0` and assumed it was correct and didn't look at the fact that it started at 10.

## Q31 count negative values

Consider an integer array `nums`, which has been properly declared and initialized with one or more values. Which of the following code segments counts the number of negative values found in `nums` and stores the count in `counter`?

I.
```java
int counter = 0;
int i = -1;
while (i <= nums.length - 2)
{
    i++;
    if (nums[i] < 0)
    {
        counter++;
    }
}
```

II.
```java
int counter = 0;
for (int i = 1; i < nums.length; i++)
{
    if (nums[i] < 0)
    {
        counter++;
    }
}
```

III.
```java
int counter = 0;
for (int i : nums)
{
    if (nums[i] < 0)
    {
        counter++;
    }
}
```

```
A) I only (Correct Answer)
B) II only
C) I and II only (Wrong Answer)
D) I and III only
E) I, II, and III
```

Since we want to find all the negative values in the int array `num`, we should just iterate through each value and increment the counter for each value below 0.

I does this, even though the conditions is `i <= nums.length - 2`, the increment is before the check, so it still iterates through all values.

II starts the loop at index 1, so it skips the first value and is incorrect.

III is completely wrong, as it iterates through all values in the array and uses that value to index the array, which makes no sense, so only I is correct here.

I chose I and II because I didn't see that the loop started at 1 for II.

## Q33 array with A through L

{% raw %}
```java
String[][] letters = {{"A", "B", "C", "D"},
                      {"E", "F", "G", "H"},
                      {"I", "J", "K", "L"}};

for (int col = 1; col < letters[0].length; col++)
{
    for (int row = 1; row < letters.length; row++)
    {
        System.out.print(letters[row][col] + " ");
    }
    System.out.println();
}
```
{% endraw %}

What is printed as a result of executing this code segment?

```
A) A E I
   F J
   K

B) B F J (Wrong Answer)
   C G K
   D H L

C) E I
   F J
   G K
   H L

D) F G H
   J K L

E) F J (Correct Answer)
   G K
   H L
```

So there are two nested loops that iterate through the letters 2D array, but the outer loop iterates through the rows and the inner loop through the columns. This means that each columns values will be iterated from top to bottom, separating each column with a newline.

Also, both of the loops start at 1, so the first column and the first value of each column are ignored.

You can just picture the 2D array without the top row and leftmost column and iterate each column from top to bottom and you will get choice E as the correct answer.

I chose choice B, because I didn't take into account that all the values in the first row are skipped.

