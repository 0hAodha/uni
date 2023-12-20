- #[[CT2106 - Object-Oriented Programming]]
- **Previous Topic:** [[Interfaces]]
- **Next Topic:** [[Sorting & Testing]]
- **Relevant Slides:** ![Lecture-21___2022.pdf](../assets/Lecture-21_2022_1668676202336_0.pdf)
-
- # Static Fields
	- What is a **static field**? #card
	  card-last-interval:: 0.98
	  card-repeats:: 1
	  card-ease-factor:: 2.36
	  card-next-schedule:: 2022-11-18T08:47:13.874Z
	  card-last-reviewed:: 2022-11-17T09:47:13.874Z
	  card-last-score:: 3
		- Up until now, the instance variables that we have used have had scope at object level.
		- A **static field** is a variable that has scope at the **class level**.
		- Typically, static fields are used to hold constant, non-changing data.
		- Often, they may be declared public & **final**.
			- This means that they can be accessed directly by other classes & objects but cannot be changed.
		- One uses static fields when one wants to declare a value/property that is unchanging or common to all objects of a class.
- Generally, static variables are CAPITALISED.
- When referencing a static field, we use the form `ClassName.<STATIC_VARIABLE_NAME>`.
	- e.g., `Cards.RANKS`.
- # Exceptions
	- What is an **Exception**? #card
	  card-last-interval:: 2.8
	  card-repeats:: 1
	  card-ease-factor:: 2.6
	  card-next-schedule:: 2022-11-20T04:45:26.574Z
	  card-last-reviewed:: 2022-11-17T09:45:26.575Z
	  card-last-score:: 5
		- An **exception** is an "exceptional event" - one that may lead to a serious error in your program if not handled appropriately.
		- An exception is generated only when the program runs - hence it is known as a **runtime error**.
			- Very often, the error (& the exception generated), occur when the program is asked to do something that is impossible for it to do.
		- In Java, each exception is represented by an **Exception Object**.
	- ## Programming for Exceptions - Throwing Exceptions
		- As the programmer, it is your responsibility to anticipate the situations in which your program will fail.
			- You have to write code to manage any exceptional events that may occur withing your program.
		- When a program generates an Exception object, it is said to *throw an Exception*.
			- When an Exception is thrown, the program must have code in place to **catch it** - otherwise the program will terminate.
		- ### Throwing an Exception #card
		  card-last-interval:: -1
		  card-repeats:: 1
		  card-ease-factor:: 2.5
		  card-next-schedule:: 2022-11-18T00:00:00.000Z
		  card-last-reviewed:: 2022-11-17T09:45:39.752Z
		  card-last-score:: 1
			- 1. Detecting an error.
			  2. Creating an Exception object.
			  3. Passing the Exception object to the Java Runtime Environment (JRE) Exception Handling  Procedures.
				- This also means that the execution of the method does not complete.
			- 4. The JRE then looks for a part of your program to take responsibility for this error.
				- In other words, your program should also have code ready to **catch** the error.
		- ### `throws` #card
		  card-last-interval:: 0.98
		  card-repeats:: 1
		  card-ease-factor:: 2.36
		  card-next-schedule:: 2022-11-18T08:46:16.012Z
		  card-last-reviewed:: 2022-11-17T09:46:16.013Z
		  card-last-score:: 3
			- When you want a method to throw an Exception, you add the keyword `throws` and the Exception type to the method signature.
				- `public Card (int suit, int rank) throws IllegalArgumentException`
				- This tells any code that wants to call the constructor method that it may throw an `IllegalArgumentException`, which indicates that a method has been passed an illegal or inappropriate argument.
			- To throw an exception, you use the `throw` keyword.
				- ```java
				  if (rank < 1 || rank > Card.RANKS.length -1) {
				    throw new IllegalArgumentException("Incorrect rank value: " + rank);
				  }
				  ```
		- ### Graceful Recovery
			- If an exception is not caught, the JRE will terminate the program.
				- This is a drastic step.
			- In most cases, you will want your program to recover gracefully from an exception & carry on.
			- This involves **catching** the Exception that has been generated.
		- ### `try` / `catch`
			- If you want the program to recover from the Exception, you have to catch & handle it.
				- This means using a `try`/`catch` expression.
			- `try`: Try to execute this piece of code.
				- If it executes without throwing an exception, fine - there is no need for the `catch` clause to be executed.
			- `catch`: If an exception has been thrown, then execute this piece of recovery code to **handle** the Exception (very often just an error message).
		- ```java
		  try {
		    // call the code that may call an Exception
		  } catch(/*TheExceptionClass variable*/) {
		    // how you want to handle the error
		  }
		  ```
	- ## Some Common Unchecked Excpetions
		- | **Name** | **Description** |
		  | `NullPointerException` | Thrown when attempting to access an object with a reference variable whose current value is `null` |
		  | `ArrayIndexOutOfBound` | Thrown when attempting to access an array with an invalid index value (either negative or beyond the length of the array). |
		  | `IllegalArgumentException` | Thrown when a method receives an argument that is not formatted how the method expects. |
		  | `IllegalStateException` | Thrown when the state of the environment doesn't match the operation being attempted, e.g., using a Scanner that has been closed. |
		  | `NumberFormatException` | Thrown when a method that converts a String to a number receives a String that it cannot convert. |
		  | `ArithmeticException` | Arithmetic error, such as divide-by-zero.|
		-