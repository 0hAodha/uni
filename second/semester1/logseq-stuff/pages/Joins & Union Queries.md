- #[[CT230 - Database Systems I]]
- **Previous Topic:** [[Entity Relationship Models]]
- **Next Topic:** [[Normalisation]]
- **Relevant Slides:** ![SQL Joins and Union Queries class.pdf](../assets/SQL_Joins_and_Union_Queries_class_1665572555489_0.pdf)
-
- # Joins
	- What are **Joins**? #card
	  card-last-interval:: 2.8
	  card-repeats:: 2
	  card-ease-factor:: 2.6
	  card-next-schedule:: 2022-11-20T04:49:32.456Z
	  card-last-reviewed:: 2022-11-17T09:49:32.458Z
	  card-last-score:: 5
		- ^^**Joins** combine multiple tables into one table.^^
			- This new (temporary) table is then queried to return results so that we can return values from any of the table that were joined.
		- Tables are joined by specifying links (**joins**) across attributes in the tables.
		- Joins are carried out on 2 tables at a time, but many tables can be joined in one.
			- For example, a third table could be joined to a table that results from joining two tables.
	- ## Specifying Joins #card
	  card-last-interval:: 0.88
	  card-repeats:: 2
	  card-ease-factor:: 2.36
	  card-next-schedule:: 2022-11-18T06:49:54.055Z
	  card-last-reviewed:: 2022-11-17T09:49:54.056Z
	  card-last-score:: 3
		- [1]. In SQL, we must specify *all the tables* that are part of the join in the `FROM` clause.
		- [2]. We must then specify the **join condition** - for an inner join, the condition is `foreign_key = primary_key / candidate_key`.
		- [3]. The join condition can be specified in the `FROM` or `WHERE` clause.
	- ## Different Types of Joins
		- **Inner Join** is the default when using an **implicit join**.
		- For **explicit joins**, we must explicitly state the join used.
		- ![https://www.csestack.org/wp-content/uploads/2020/10/sql-table-joins.png](https://www.csestack.org/wp-content/uploads/2020/10/sql-table-joins.png)
		- ### Inner Joins #card
		  card-last-interval:: 0.88
		  card-repeats:: 2
		  card-ease-factor:: 2.36
		  card-next-schedule:: 2022-11-18T06:49:01.559Z
		  card-last-reviewed:: 2022-11-17T09:49:01.559Z
		  card-last-score:: 3
			- An `INNER JOIN` includes the tuples from the first (left) of the two tables ^^only when they satisfy the join condition^^ and tuples from the second (right) table ^^only when they also satisfy the join condition.^^
			- Example:
				- ```sql
				  SELECT *
				  FROM employee INNER JOIN dependent
				  	ON ssn = essn;
				  ```
		- ### Left Joins #card
		  card-last-interval:: 0.98
		  card-repeats:: 2
		  card-ease-factor:: 2.36
		  card-next-schedule:: 2022-11-15T19:06:39.454Z
		  card-last-reviewed:: 2022-11-14T20:06:39.455Z
		  card-last-score:: 3
			- **Left (outer) joins** include all of the tuples from the first (left) of two tables, regardless of whether or not they satisfy the join condition or if there are matching values in the second (right) table.
			- Tuples from the second (right) table are only included when they satisfy the join condition.
			- [Essentially the same as right joins.]
			- Example:
				- ```sql
				  SELECT *
				  FROM employee LEFT JOIN department ON
				  	 employee.ssn = department.mgrssn;
				  ```
		- ### Right Joins #card
		  card-last-interval:: 2.8
		  card-repeats:: 2
		  card-ease-factor:: 2.6
		  card-next-schedule:: 2022-11-20T04:49:38.317Z
		  card-last-reviewed:: 2022-11-17T09:49:38.317Z
		  card-last-score:: 5
			- **Right (outer) joins** include all of the tuples from the second (right) table, regardless of whether or not they satisfy the join condition or if there are matching values in the first (left) table.
			- Tuples from the first (left) table are include only if the satisfy the join condition.
			- [Essentially the same as left joins.]
			- Example:
				- ```sql
				  SELECT *
				  FROM employee RIGHT JOIN department ON
				  	 employee.ssn = department.mgrssn;
				  ```
	- ## Inner Joining Tables #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T20:05:20.530Z
	  card-last-score:: 1
		- The results of an inner join operation between two tables $R(A_1, A_2, \dots, A_n)$ and $S(B_1, B_2, \dots, B_m)$ is a table $Q(A_1, A_2, \dots, A_n, B_1, B_2, \dots, B_m)$.
		- $Q$ has one tuple for each combination of tuples (one from $R$ & one from $S$) ^^whenever the combination satisfies the join condition^^ - the join will retrieve **all** attributes in each table.
		- ### Example: Inner Join Condition for the `employee` & `dependent` Tables
			- **Join Condition:** `ssn = essn`.
			- Full query retrieving all employees & their dependents (*dependants* in non-American English), when they have dependents:
				- ```SQL
				  SELECT *
				  FROM employee INNER JOIN dependent
				  	ON ssn = essn;
				  ```
		- #### Note
			- When attributes with the same name from different tables are used in a join query, you need to specify the table name to avoid ambiguity.
			- For example:
				- `bdate` in `employee` & `dependent`.
					- We can refer to these unambiguously as `employee.bdate` & `dependent.bdate`.
					-
	- ## Implicit & Explicit Joins
		- The **join condition** can be specified *implicitly* or *explicitly*.
		- What is an **explicit join**? #card
		  card-last-interval:: 3.05
		  card-repeats:: 2
		  card-ease-factor:: 2.6
		  card-next-schedule:: 2022-11-17T21:24:32.854Z
		  card-last-reviewed:: 2022-11-14T20:24:32.854Z
		  card-last-score:: 5
			- An **explicit join** is specified in the `FROM` clause where the tables to be joined are listed.
				- The keyword `INNER JOIN` is used for inner joins, and the **join condition** is listed using the keyword `ON`.
				- Syntax:
					- ```SQL
					  SELECT [DISTINCT] <attribute list>
					  FROM <table>
					  	[INNER / LEFT / RIGHT] JOIN <table>
					      ON <join condition>
					  WHERE <condition>
					  ```
		- What is an **implicit join**? #card
		  id:: 6346a49e-2951-4bdb-ab74-6920aa664c41
		  card-last-interval:: 0.98
		  card-repeats:: 2
		  card-ease-factor:: 2.36
		  card-next-schedule:: 2022-11-15T19:05:12.117Z
		  card-last-reviewed:: 2022-11-14T20:05:12.118Z
		  card-last-score:: 3
			- An **implicit join** is specified on the `WHERE` clause without using the keyword `ON`.
				- It is referred to as a **join condition**.
				- All the tables must be listed in the `FROM` clause, separated by commas.
				- The **join condition** is contained in the `WHERE` clause.
					- If there are other conditions, the join condition is appended on with `AND`.
					- Other conditions can be specified in the `WHERE` clause as well as the join condition.
				- All implicit joins are **inner joins** -  all rows from both tables will be returned whenever there is a match between the attributes in the join table.
				- Syntax:
					- ```sql
					  SELECT [DISTINCT] <attribute list>
					  FROM <table>, <table>
					  WHERE <join condition> AND
					        <condition>
					  ```
	- ## Self-Joins & Aliases
		- What is a **self-join**? #card
		  card-last-interval:: 2.8
		  card-repeats:: 2
		  card-ease-factor:: 2.6
		  card-next-schedule:: 2022-11-17T15:08:05.288Z
		  card-last-reviewed:: 2022-11-14T20:08:05.289Z
		  card-last-score:: 5
			- A **self-join** is a normal SQL join that joins a table to itself.
				- This is accomplished by using **aliases** to give each "instance" of the table a separate name using the keyword `AS`.
- # Sub-Queries VS Joins
	- Can sub-queries & joins be used interchangeably? #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.22
	  card-next-schedule:: 2022-11-21T19:35:33.146Z
	  card-last-reviewed:: 2022-11-17T19:35:33.147Z
	  card-last-score:: 3
		- In some cases, you can replace a join with a sub-query.
		- But recall:
			- Joins are needed when values across multiple tables must be displayed.
			- Sub-queries are needed when an existing value from a table needs to be retrieved & used as part of the query solution.
			- Sub-queries are needed when an aggregate function needs to be performed & used as part of a query solution.
- # Union Queries
	- The keyword `UNION` is used to combine the results of two or more queries or tables.
	- MySQL does not support minus or intersection (intersect) operators, but the same functionality can be built using join.
	- For union queries, tables must be **union compatible**.
	- What does it mean to be **union compatible**? #card
	  card-last-interval:: 2.8
	  card-repeats:: 2
	  card-ease-factor:: 2.6
	  card-next-schedule:: 2022-11-17T15:08:42.023Z
	  card-last-reviewed:: 2022-11-14T20:08:42.023Z
	  card-last-score:: 5
		- Two relations are **union compatible** if the schemas of two relations match.
			- i.e., there are the same number of attributes in each relation, and each pair of corresponding attributes have the same **domain**.
-
-