- #[[CT2106 - Object-Oriented Programming]]
- **Previous Topic:** [[Introduction to Object-Oriented Programming]]
- **Next topic:** [[First Java Code]]
- **Relevant Slides:** ![Lecture01.pdf](../assets/Lecture01_1662850796416_0.pdf)
-
- ## High-Level Language
	- Both Java and C are **high-level languages** and Assembly is a **low-level language**.
	- "**High-Level**" is a *relative* term.
		- A **High-Level Language** is the level of abstraction above a **Low-Level Language**.
		- A **Low-Level Language** has little or no abstraction over the machine code of a particular processor.
	- ### Advantages of High-Level Programming Languages
		- Easier to program.
		- Syntax can be understood by people.
		- Programs take less time to write, are shorter, & easier to read, so they are more likely to be correct.
		- Portable - they can be run on different kinds of computers.
		-
-
- ## Translating Your Code
	- Unless you are writing Machine Code, your code has to be translated into machine code to be run on your computer.
	- There are two types of translation:
		- 1. **Compilation**.
		  2. **Interpretation**
	-
	- ### Compilation
		- What is a **Compiler**? #card
		  card-last-interval:: 33.64
		  card-repeats:: 4
		  card-ease-factor:: 2.9
		  card-next-schedule:: 2022-12-18T07:44:08.310Z
		  card-last-reviewed:: 2022-11-14T16:44:08.310Z
		  card-last-score:: 5
			- A **compiler** is a program that takes human-readable source code and translates it in one go into Machine Code.
			- With compilation, "translation" occurs ^^before the program is run.^^
		- Machine Code generated by compilation is **not portable**.
		- However, the generated Machine Code typically executes **very efficiently**.
		- For big projects, the compile time can be slow.
	- ### Interpretation
		- Code is "translated"" on-the-fly at runtime into commands that can be executed on the machine.
		- Code is read & executed by the **interpreter** when the program is run.
		- Interpreted code is **portable** (as long as there is an interpreter).
		- Typically, interpreted code is ^^slower to run^^ as each statement has to be interpreted into machine code **on-the-fly**.
		- Greater chance of run-time errors.
	-
	- ^^Java is typically both *compiled* **and** *interpreted*.^^
		- ^^Java is **compiled** to *Byte Code* - an *intermediate language* which is portable.^^
		- ^^This Byte Code is then **read** and **executed** by a Java **interpreter**.^^
	-
-
- ## Java Virtual Machine (JVM)
	- What is the **JVM**? #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:38:43.908Z
	  card-last-reviewed:: 2022-11-14T16:38:43.909Z
	  card-last-score:: 5
		- The **Java Virtual Machine (JVM)** is a piece of software - a *virtual computer* upon which **Java byte code** is executed.
	- What is the **JRE**? #card
	  card-last-interval:: 28.3
	  card-repeats:: 4
	  card-ease-factor:: 2.66
	  card-next-schedule:: 2022-12-12T23:43:23.817Z
	  card-last-reviewed:: 2022-11-14T16:43:23.818Z
	  card-last-score:: 3
		- The **Java Runtime Environment (JRE)** contains the JVM and all the libraries required to run the Java progam.
-