# Calculator implementation

> Problem: Calculator
  Write some code to calculate a result from a set of instructions. Instructions comprise
  of a keyword and a number that are separated by a space per line. Instructions are loaded
  from file and results are output to the screen. Any number of Instructions can
  be specified. Instructions can be any binary operators of your choice (e.g., add, divide,
  subtract, multiply etc). The instructions will ignore mathematical precedence. The last
  instruction should be “apply” and a number (e.g., “apply 3”). The calculator is then
  initialised with that number and the previous instructions are applied to that numberSubtitle or Short Description Goes Here


Examples of the calculator lifecycle might be:


## Example 
[Input from file]

add 2

multiply 3

apply 4



## Example 1.

[Output to screen]

18

[Explanation]

(4 + 2) * 3 = 18


##Example 2.
[Input from file]

multiply 9

apply 5

[Output to screen]

45

[Explanation]

5 * 9 = 45

## Example 3.
[Input from file]

apply 1

[Output to screen]

1

Requeriments
---
- Java 14
- Apache Maven 3.5.4

How to launch application
===
 - Clone this repo to your local machine using https://github.com/VitaliiBashta/Calculator.git
 - build project with <code>mvn clean package</code>
 - launch <code>java -jar </code>