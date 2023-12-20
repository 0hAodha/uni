- #[[CT2106 - Object-Oriented Programming]]
- **Previous Topic:** [[Introduction to Inheritance]]
- **Next Topic:** [[Abstraction & Polymorphism]]
- **Relevant Slides:** ![Lecture 11 and Lecture 12 ___2022.pdf](../assets/Lecture_11_and_Lecture_12_2022_1665648153439_0.pdf)
-
- # Key Ideas in a Class Hierarchy
	- The top of the hierarchy represents the most generic attributes & behaviours.
	- The bottom (the leaves) represent the most specific attributes & behaviours.
	- Each level inherits & customises the attributes & behaviours from the level above it.
	- What is **OOP Inheritance**? #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.7
	  card-next-schedule:: 2022-11-22T18:35:20.846Z
	  card-last-reviewed:: 2022-11-18T18:35:20.847Z
	  card-last-score:: 5
		- **OOP Inheritance** is the means by which objects automatically receive features (fields) & behaviours (methods) from their super classes.
- # Java Class Hierarchy
	- At the top of the Java Class Hierarchy is a class called `java.lang.Object`.
	- All classes inherit *implicitly* from `java.lang.Object`.
		- This means that a class doesn't have to specify explicitly that `java.lang.Object` is its superclass.
	- ## Rules of Class Hierarchy
		- In Java, the variable type can be the superclass of the object.
			- The variable type can be **any superclass** of the object, not just `java.lang.Object`.
-
- # Explicit Inheritance
	- All classes inherit methods *implicitly* from `java.lang.Object`.
		- Two common methods that are inherited from `java.lang.Object`:
			- `equals()`
			- `toString()`
	- In every other case, you have to tell Java which classes are in a superclass relationship.
	- ## Steps
		- 1. Create the classes
		  2. Inert the inheritance relationships.
		  3. Insert the fields.
		  4. Insert the methods.
		  5. Override the necessary fields.
		  6. Override necessary methods.
		  7. Test by putting objects in an array & calling their behaviours.
	- ## Defining Inheritance #card
	  card-last-interval:: 3.45
	  card-repeats:: 2
	  card-ease-factor:: 2.46
	  card-next-schedule:: 2022-11-18T06:10:29.757Z
	  card-last-reviewed:: 2022-11-14T20:10:29.758Z
	  card-last-score:: 3
		- The keyword `extends` indicates the subclass to be extended (inherited from).
		- You must call the constructor of the superclass using the method call `super()`.
			- If the superclass constructor takes a parameter, then the call to `super()` must include a value of the parameter.
		- For example:
			- ```java
			  public class Bird extends Animal
			  {
			  	boolean hasFeathers;	// these fields aren't private.
			      boolean hasWings;		// we want these fields to be inherited
			      boolean flies;			// so we don't make the private.
			      
			      public Bird()
			      {
			      	super();	// calls the constructor of its superclass - Animal
			          colour = "black";
			          hsaFeathers = true;
			          hasWings = true;
			          flies = true;
			      }
			  }
			  ```
- # `abstract` #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.7
  card-next-schedule:: 2022-11-18T20:09:22.323Z
  card-last-reviewed:: 2022-11-14T20:09:22.324Z
  card-last-score:: 5
	- It may not make sense to have an object of a superclass type.
		- For example, there is no object that is just an `Animal` or `Bird` and no more than that - all Animals are a specific subclass of `Animal`.
	- ^^The Java keyword `abstract` allows you to specify which classes can be made into objects and which are used for inheritance purposes.^^
		- Adding the keyword `abstract` to the class definition tells Java that it can't make objects from this class.
		- However, an abstract class can still be used as a type of reference variable.
			- ```java
			  Bird bird = new Canary("John");
			  Animal animal = new Canary("Mary");
			  ```
	- For example:
		- ```java
		  public abstract class Animal	// doesn't allow objects of just type Animal
		  ```
		- ```java
		  public abstract class Bird extends Animal
		  ```