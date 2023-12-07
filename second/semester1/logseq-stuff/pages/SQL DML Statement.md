- #[[CT230 - Database Systems I]]
- **Previous Topic:** [[Introduction to SQL & DDL]]
- **Next Topic:** [[SQL DML: SELECT]]
- **Relevant Slides:** ![Topic 4_DML_SQL_part1.pdf](../assets/Topic_4_DML_SQL_part1_1663680318777_0.pdf)
-
- # Notes on Syntax
	- While SQL is case insensitive, Linux ^^is not.^^
		- web1.cs.nuigalway.ie is a ^^linux server^^, so we need to be careful with table names in particular, as `EMPLOYEE` is ^^not the same thing^^ as `employee`.
	- Attribute names are separated by commas.
	- Strings are enclosed in quotes.
	- Numbers are not enclosed in  quotes.
	-
	- Usually, SQL keywords are capitalised while table & keyword names are mostly kept in lowercase unless using camelCase instead of snake_case.
	- Code should be organised horizontally & vertically, and not all written on one line.
	- Code blocks are separated by a semicolon.
	- Comments can be made using `#`, `--`, & `/*  */`.
-
- # **CRUD** Operations in DML
	- What are **CRUD** Operations? #card
	  card-last-interval:: 31.36
	  card-repeats:: 4
	  card-ease-factor:: 2.8
	  card-next-schedule:: 2022-12-16T04:09:37.257Z
	  card-last-reviewed:: 2022-11-14T20:09:37.258Z
	  card-last-score:: 5
		- **CRUD** operations are the 4 basic functions that we may wish to perform on *persistent* data.
			- **Create:** insert a new tuple. `INSERT`
			- **Read:** retrieve some data. `SELECT`
			- **Update:** modify some data. `Update`
			- **Delete:** delete some data or a tuple. `DELETE`
	- ## Read: `SELECT`
		- The basic syntax for an SQL select query to *read* data consists of 6 clauses. #card
		  collapsed:: true
		  card-last-interval:: 19.01
		  card-repeats:: 4
		  card-ease-factor:: 2.18
		  card-next-schedule:: 2022-12-03T20:06:02.473Z
		  card-last-reviewed:: 2022-11-14T20:06:02.473Z
		  card-last-score:: 3
			- ```sql
			  SELECT [DISTINCT] <attribute list>
			  FROM <table list>
			  WHERE <condition>
			  GROUP BY <group attributes>
			  HAVING <group condition>
			  ORDER BY <attribute list>
			  ```
			- The order of these clauses ^^cannot be changed.^^
			- `SELECT` & `FROM` are ^^always required^^, other clauses are optional.
		- ### `SELECT <attribute list> FROM <table list> WHERE <condition>` #card
		  collapsed:: true
		  card-last-interval:: 108
		  card-repeats:: 5
		  card-ease-factor:: 3
		  card-next-schedule:: 2023-03-06T18:36:40.152Z
		  card-last-reviewed:: 2022-11-18T18:36:40.153Z
		  card-last-score:: 5
			- `<attribute list>` is a list of **attribute** (column) names (separated by commas) whose values will be retrieved by the query.
			- `<table list>` is a list of table names (separated by commas) containing the attributes.
			- `<condition>` is a **Boolean** expression that identifies the tuples to be retrieved by the query.
				- For each **tuple** (row) in the table(s) which are part of the query:
					- the tuple is checked to see if the condition is **true** for this tuple.
						- If **true**, the tuple **is** part of the output.
						- If **false**, the tuple is **not** part of the output.
				- The comparison operators are:
					- `=` `<=` `<` `>` `>=` `!=`
					- Conditions can be compounded by use of Boolean `AND`, `OR`, and can be negated with `NOT`.
					- ^^Note:^^ In some versions of SQL, (e.g., in MS), the `!=` operator is written as `<>`.
		- ### Calculated or Derived Fields #card
		  collapsed:: true
		  card-last-interval:: 28.3
		  card-repeats:: 4
		  card-ease-factor:: 2.66
		  card-next-schedule:: 2022-12-15T16:48:02.726Z
		  card-last-reviewed:: 2022-11-17T09:48:02.727Z
		  card-last-score:: 5
			- We can specify an SQL expression in the `SELECT` clause which can involve **numerical operations** on **numeric** fields and **counting operations** on **non-numeric** fields.
			- e.g.,
				- ```sql
				  SELECT ssn, salary/12 FROM employee;
				  ```
		- ### Tidying Up the Output #card
		  card-last-interval:: 19.01
		  card-repeats:: 4
		  card-ease-factor:: 2.18
		  card-next-schedule:: 2022-12-03T20:05:39.227Z
		  card-last-reviewed:: 2022-11-14T20:05:39.227Z
		  card-last-score:: 3
			- We can use keywords `CAST`, `AS`, & `DECIMAL(x, y)` to specify the total number of digits `x` and the number of digits after the decimal point `y` when we're working with **real numbers**.
				- ```sql
				  SELECT ssn, CAST(salary/12.0 AS DECIMAL(8,2))
				  FROM employee;
				  ```
			- We can also use the keyword `AS` to rename output.
				- ```sql
				  SELECT ssn, CAST(salary/12.0 AS DECIMAL(8,2))
				  AS monthlySalary
				  FROM employee;
				  ```
			-
		- ### Keyword `DISTINCT` #card
		  card-last-interval:: 33.64
		  card-repeats:: 4
		  card-ease-factor:: 2.9
		  card-next-schedule:: 2022-12-21T00:50:04.359Z
		  card-last-reviewed:: 2022-11-17T09:50:04.360Z
		  card-last-score:: 5
			- The keyword `DISTINCT` automatically removes duplicates from the returned result set.
			- We should be careful of using with large result sets as it can be an expensive operation to perform (not a problem for our small examples).
			- ```sql
			  SELECT DISTINCT salary
			  FROM employee;
			  ```
		- ### Selecting All Attribute Values for Selected Tuples #card
		  card-last-interval:: 11.2
		  card-repeats:: 3
		  card-ease-factor:: 2.8
		  card-next-schedule:: 2022-11-25T20:36:44.458Z
		  card-last-reviewed:: 2022-11-14T16:36:44.458Z
		  card-last-score:: 5
			- To retrieve all attribute values of selected tuples, you do not have to explicitly list all the attribute names - instead, you can use `SELECT *`.
		- ### New Operators
		  collapsed:: true
			- `BETWEEN` #card
			  card-last-interval:: 33.64
			  card-repeats:: 4
			  card-ease-factor:: 2.9
			  card-next-schedule:: 2022-12-18T11:07:14.075Z
			  card-last-reviewed:: 2022-11-14T20:07:14.075Z
			  card-last-score:: 5
				- The `BETWEEN` operator selects values within a given range. The values can be numbers, texts, or dates.
				- ```sql
				  SELECT <column name(s)>
				  FROM <table name>
				  WHERE <column name(s)> BETWEEN <value 1> AND <value 2>;
				  ```
			- `IN` #card
			  card-last-interval:: -1
			  card-repeats:: 1
			  card-ease-factor:: 2.56
			  card-next-schedule:: 2022-11-18T00:00:00.000Z
			  card-last-reviewed:: 2022-11-17T09:48:15.246Z
			  card-last-score:: 1
				- The `IN` operator tests if a data value matches one of a list of values.
				- It is a shorthand for multiple `OR` conditions.
				- ```sql
				  SELECT <column name(s)>
				  FROM <table_name>
				  WHERE <column_name> IN (<value1>, <value2>, ...); 
				  
				  -- OR --
				  
				  SELECT <column_name(s)>
				  FROM <table_name>
				  WHERE <column_name> IN (SELECT <statement>); 
				  ```
			- `LIKE` #card
			  card-last-interval:: 33.64
			  card-repeats:: 4
			  card-ease-factor:: 2.9
			  card-next-schedule:: 2022-12-21T00:50:18.687Z
			  card-last-reviewed:: 2022-11-17T09:50:18.687Z
			  card-last-score:: 5
				- Allows string comparison, when equality is too strict.
				- There are 2 wildcards, often used in conjunction with the `LIKE` operator:
					- The percent sign `%` represents zero, one, or multiple characters.
					- The underscore sign `_` represents one, single character.
				- ```sql
				  SELECT <column_name(s)>
				  FROM <table_name>
				  WHERE <column_name> LIKE <pattern>; 
				  ```
			- `IS NULL` #card
			  card-last-interval:: 33.64
			  card-repeats:: 4
			  card-ease-factor:: 2.9
			  card-next-schedule:: 2022-12-18T07:38:30.380Z
			  card-last-reviewed:: 2022-11-14T16:38:30.380Z
			  card-last-score:: 5
				- Allows an explicit search for `NULL`.
				-
			-
		- ### Set Operators
			- `UNION`
			- `INTERSECTION`
			- `MINUS / DIFFERENCE`
-
-
- ```SQL
  SELECT full_name, ssn FROM (
      SELECT ssn, CONCAT(fname, " ", minit, " ", lname)
      AS full_name from employee) AS dtable
  WHERE full_name IN ("John B Smith", "James E Borg", "Joyce A English");
  ```