- #[[CT2106 - Object-Oriented Programming]]
- **Previous Topic:** [[Abstraction & Polymorphism]]
- **Next Topic:** [[Static Fields & Exceptions]]
- **Relevant Slides:** ![Lecture-15__2022.pdf](../assets/Lecture-15_2022_1666857889307_0.pdf) ![Lecture-16__2022.pdf](../assets/Lecture-16_2022_1666857979984_0.pdf)
-
- # Multiple Inheritance
	- What is **multiple inheritance**? #card
	  card-last-interval:: 3.05
	  card-repeats:: 2
	  card-ease-factor:: 2.6
	  card-next-schedule:: 2022-11-17T21:22:31.707Z
	  card-last-reviewed:: 2022-11-14T20:22:31.708Z
	  card-last-score:: 5
		- **Multiple inheritance** is where a class has multiple simultaneous superclasses.
		- However, Java does not support multiple inheritance as it has led to major problems in OOP due to conflicting field & method implementations inherited from superclasses.
- # Interface
	- What is an **interface**? #card
	  card-last-interval:: 3.05
	  card-repeats:: 2
	  card-ease-factor:: 2.6
	  card-next-schedule:: 2022-11-17T21:25:49.658Z
	  card-last-reviewed:: 2022-11-14T20:25:49.659Z
	  card-last-score:: 5
		- Java uses a structure called an **interface** to achieve a form of *multiple inheritance*.
			- An interface is like a class, but it is really more like ==an outline of what methods a class should have.==
		- Like a class, an interface can be used as a **type**.
		- By convention, interface names often end in -`able`.
		- While a class can only extend one super class (direct inheritance), a class can implement multiple interfaces.
	- ## Examples
		- ```java
		  public interface Eatable
		  {
		    public int getCalories();	// note: method definitions have no body
		    public int extractEnergy;
		  }
		  ```
		- ```java
		  public class Canary extends Bird implements Food, Comparable() ....
		  ```
	- What does it mean if a class implements an interface? #card
	  card-last-interval:: 0.98
	  card-repeats:: 1
	  card-ease-factor:: 2.36
	  card-next-schedule:: 2022-11-15T15:21:59.543Z
	  card-last-reviewed:: 2022-11-14T16:21:59.544Z
	  card-last-score:: 3
		- 1. Any class that implements said interface can be treated as that interface's type (polymorphism).
		  2. Any class that implements that interface *must* provide **concrete implementations** of its method.
	- ## Interface VS Abstract
		- ### Similarities
			- Both can be used to provide "templates" for what subclasses can implement.
			- An abstract method plays the same role as an interface method - both *must* be implemented in concrete form by a subclass.
			- An abstract class and an Interface can be used as the **type** for a reference variable.
		- ### DIfferences
			- An abstract class is used for class inheritance purposes - providing an abstract structure that subclasses inherit. Therefore, the subclasses have a lot in common.
			- However, an interface is often used to impose common functionality on classes that have nothing in common.
			-