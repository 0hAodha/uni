- #[[CT2106 - Object-Oriented Programming]]
- **Previous Topic:** [[Variables & Types]]
- **Next Topic:** [[Introduction to Inheritance]]
- **Relevant Slides:** ![Lecture-6__2022.pdf](../assets/Lecture-6_2022_1663835887381_0.pdf) ![Lecture-7__2022.pdf](../assets/Lecture-7_2022_1664439118886_0.pdf) ![Lecture-8__2022.pdf](../assets/Lecture-8_2022_1664528150319_0.pdf)
-
- # Modelling the Problem
	- A major part of OOP is modelling the problem.
	- The goal is to identify the **principle objects** in the problem domain, which we model as classes, the **responsibility** of each of these objects, and the **collaborations** between objects.
	- The objective of OOP Modelling is to produce a simplified **class diagram**.
		- **Classes** represent real-world entities.
		- **Associations** represent collaborations between the entities.
		- **Attributes** represent the data held about these entities.
		- **Generalisation** can be used to simplify the structure of the model.
	- What are **nouns** in OOP?
	  card-last-score:: 5
	  card-repeats:: 4
	  card-next-schedule:: 2022-12-15T02:37:38.303Z
	  card-last-interval:: 33.64
	  card-ease-factor:: 2.9
	  card-last-reviewed:: 2022-11-11T11:37:38.304Z
		- **Nouns** are candidate objects in OOP.
- # OOP Principles
	- Consider the following principles when assigning responsibilities:
		- An **Object** is responsible for its own data.
			- An Object is responsibility for communicating its state.
		- **Single Responsibility Principle:** Each **Class** should have a ^^single responsibility.^^
			- All its services should be aligned with that responsibility.