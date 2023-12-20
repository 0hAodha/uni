- #[[CT2106 - Object-Oriented Programming]]
- **Previous Topic:** [[Static Fields & Exceptions]]
- **Next Topic:** No Next Topic.
- **Relevant Slides:**  ![Lecture-22___2022.pdf](../assets/Lecture-22_2022_1668765583448_0.pdf)
-
- # Natural Ordering
	- When deciding on whether one object is greater than or less than another, we refer to the **natural ordering** of the object's class.
	- Natural ordering is the ordering imposed on an object when its class implements the **Comparable** interface.
		- ```java
		  public interface Comparable<T>
		  ```
		- This interface imposes a total ordering on the objects of each class that implements it.
		- The class's `compareTo()` method is referred to as its **natural comparison method**.
		- Lists (and arrays) that implement this interface can be sorted automatically by `Collections.sort`.
		- Objects that implement this interface can be used as keys in a sorted map or as elements in a sorted set, without the need to specify a comparator.
		- The `<T>` in `Comparable<T>` means that we can specify in advance the types of the object that should be compared.
		- It returns a negative integer, zero, or a positive integer depending on whether the object is less than, equal to, or greater than the specified object.
- # `assert`
	- Use `assert` to declare that a statement **must be true**.
		- If it is not true, your program will throw an `AssertionError` Exception.
		- You can use the `assert` statement as a quick way to test for expected output.
		- ```java
		  assert(2==2);
		  ```