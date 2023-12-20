- #[[CT2106 - Object-Oriented Programming]]
- **Previous Topic:** [[Coding Up Inheritance]]
- **Next Topic:** [[Interfaces]]
- **Relevant Slides:** ![Lecture-13__2022.pdf](../assets/Lecture-13_2022_1666253165381_0.pdf) ![Lecture-14___2022.pdf](../assets/Lecture-14_2022_1666343488472_0.pdf)
-
- Why use an abstract class?
	- You should use an abstract class in situations where you want to use inheritance but do not want another developer to create an object from the superclass.
-
- # Abstract Methods
	- Abstract classes can also have **abstract methods**.
	- Abstract methods are methods with no body.
		- ```java
		  public abstract void sing();
		  ```
		- In other words, they do nothing.
	- Abstract methods provide the definition of a method that at least one of its subclasses must implement.
-
- # Concrete
	- The adjective **concrete** is often used in OOP to denote a class or method that is **not abstract**.
		- i.e., the class or method is fully implemented.
-
- # Reference Type
	- An abstract class is often used as the type of a reference variable.
		- ```java
		  Animal animal = new Canary("bruh");
		  ```
-
- # Polymorphism
	- What is **polymorphism**? #card
	  card-last-interval:: 3.45
	  card-repeats:: 2
	  card-ease-factor:: 2.46
	  card-next-schedule:: 2022-11-18T06:09:31.768Z
	  card-last-reviewed:: 2022-11-14T20:09:31.768Z
	  card-last-score:: 5
		- **Polymorphism** refers to how an object can be treated as belonging to several types as long as those types are **higher** than the object's type in the class hierarchy.
	- In general, a variable of type $X$ can point to any object that has an "is-a" relationship to type $X$.
		- e.g., a variable of type `Animal` can point to a `Bird`, `Frog`, or `Fish` object.