- #[[CT230 - Database Systems I]]
- No previous topic
- **Next Topic:** [[The Relational Model]]
- **Relevant Slides:** ![Lecture01.pdf](../assets/Lecture01_1662845512365_0.pdf)
-
- What is a **database**? #card
  card-last-interval:: 64.01
  card-repeats:: 5
  card-ease-factor:: 2.52
  card-next-schedule:: 2023-01-24T13:09:54.692Z
  card-last-reviewed:: 2022-11-21T13:09:54.692Z
  card-last-score:: 5
	- A **database** is ^^a collection of related data.^^
-
- What is the **Database Approach**? #card
  card-last-interval:: 7.76
  card-repeats:: 3
  card-ease-factor:: 1.94
  card-next-schedule:: 2022-11-22T10:36:04.634Z
  card-last-reviewed:: 2022-11-14T16:36:04.634Z
  card-last-score:: 3
	- A **single repository** of data (which may be distributed) is maintained that is **defined once** and then accessed by various users via a **DBMS**.
- ## Database Management Systems
	- What is a **DBMS**? #card
	  card-last-interval:: 19.01
	  card-repeats:: 4
	  card-ease-factor:: 2.18
	  card-next-schedule:: 2022-12-03T16:40:07.862Z
	  card-last-reviewed:: 2022-11-14T16:40:07.862Z
	  card-last-score:: 5
		- The **DataBase Management System (DBMS)** is a collection of programs that facilitates the process of ^^defining, constructing, & manipulating^^ databases for various applications.
	- ### DBMS Capabilities #card
	  card-last-interval:: 19.01
	  card-repeats:: 4
	  card-ease-factor:: 2.18
	  card-next-schedule:: 2022-12-03T16:44:40.304Z
	  card-last-reviewed:: 2022-11-14T16:44:40.304Z
	  card-last-score:: 3
		- 1. **Define** database (DDL)
		  2. **Manipulate** database (SQL)
		  3. **Control** redundancy
		  4. **Restrict** unauthorised access
		  5. **Enforce** integrity constraints
		  6. Provide multiple user interfaces / **views**
		  7. Provide **concurrent access**
		  8. Provide mechanism for **recovery**
		  9. Provide **back-up**
		  10. Allows representation of complex relationships between data (For efficiency & optimisation reasons)
		-
	- ### Disadvantages of DBMS approach #card
	  card-last-interval:: 23.43
	  card-repeats:: 4
	  card-ease-factor:: 2.42
	  card-next-schedule:: 2022-12-08T02:39:24.701Z
	  card-last-reviewed:: 2022-11-14T16:39:24.701Z
	  card-last-score:: 3
		- Strict schema & multiple tables / relations
		- Complexity
		- Size
		- Cost of DBMS
		- Additional hardware costs
		- Cost of conversion
		- Performance
		- Higher impact of failure
	- ### DBMS Users #card
	  card-last-interval:: 7.76
	  card-repeats:: 3
	  card-ease-factor:: 1.94
	  card-next-schedule:: 2022-11-22T10:36:32.717Z
	  card-last-reviewed:: 2022-11-14T16:36:32.718Z
	  card-last-score:: 3
		- **Administrators (DBA)** - accounts, passwords, privileges. Requiring constant vigilance
		- **System Analysts** - "What's required to solve a problem? What does the business need?"
		- **Designers** - ER diagrams, mapping ER diagrams to tables
		- **Application Programmers** - creating tables, adding data, creating queries
		- **End users**
-
- What is **Database Abstraction**? #card
  card-last-interval:: 33.96
  card-repeats:: 5
  card-ease-factor:: 2.04
  card-next-schedule:: 2022-12-18T19:23:19.167Z
  card-last-reviewed:: 2022-11-14T20:23:19.167Z
  card-last-score:: 3
	- **Database Abstraction** refers to the hiding of the details of data storage that are not needed by most database users.
	- The aim is to separate user's views of the database from the way that it is "physically" represented.
	- 3 ways in which data can be described:
		- **External:** user's view
		- **Conceptual:** logical structure as seen by DBA
		- **Internal:** DBMS and OS view of data
- What is the database **schema**? #card
  card-last-interval:: 4
  card-repeats:: 2
  card-ease-factor:: 2.52
  card-next-schedule:: 2022-11-18T20:24:46.045Z
  card-last-reviewed:: 2022-11-14T20:24:46.046Z
  card-last-score:: 5
	- The database **schema** is the ^^logical structure of the database.^^
- What is the database **instance**? #card
  card-last-interval:: 33.64
  card-repeats:: 4
  card-ease-factor:: 2.9
  card-next-schedule:: 2022-12-18T11:00:02.004Z
  card-last-reviewed:: 2022-11-14T20:00:02.004Z
  card-last-score:: 5
	- The database **instance** is ^^the actual content of the database at some point in time.^^
-
-