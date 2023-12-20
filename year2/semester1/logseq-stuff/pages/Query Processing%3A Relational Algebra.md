- #[[CT230 - Database Systems I]]
- **Previous Topic:** [[Normalisation]]
- **Next Topic:** [[Query Processing & Optimisation]]
- **Relevant Slides:** ![queryProcRelAlgebra.pdf](../assets/queryProcRelAlgebra_1667899219134_0.pdf)
-
- # Query Processing
	- What is **Query Processing**? #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T16:16:17.961Z
	  card-last-score:: 1
		- **Query Processing** transforms SQL (a high-level language) into a correct & efficient low-level language representation of **relational algebra**.
		- Each relational algebra operator has code associated with it, which, when run, performs the operation on the data specified, allowing the specified data to be output as the result.
	- ## Steps Involved in Processing an SQL Query #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T15:52:53.439Z
	  card-last-score:: 1
		- 1. Process (Parse & Translate) the query and create an internal representation of the query.
			- This may be an Operator Tree, Query Tree, or Query Graph (for more complicated queries).
		- 2. Optimise.
		  3. Execute / Evaluate returning results.
	- What do you need to translate SQL to Relational Algebra? #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T16:21:00.003Z
	  card-last-score:: 1
		- To translate SQL to Relational Algebra, you must have a meaningful set of relational algebra operators, and a mapping (translation) between SQL code & relational algebra expressions.
- # Relational Algebra
	- Two formal languages exist for the relational model:
		- **Relational Algebra** (procedural).
		- **Relational Calculus** (non-procedural).
	- Both are logically equivalent.
	- ## Relational Algebra Operations
		- A basic set of operations exist for the relational model.
			- These allow for the specification of basic retrieval requests.
		- A sequence of Relational Algebra (RA) operations forms a **relational algebra expression**.
		- RA operations are divided into two groups:
			- Operations based on **mathematical set theory** (e.g., union, product, etc.).
			- Specific relational database operations.
	- ## Relational Algebra vs SQL #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T15:53:39.774Z
	  card-last-score:: 1
		- The core operations & functions (i.e., programs) in the internal modules of most relational database systems are based on **relational algebra**.
		- SQL is a **declarative language** - It allows you to specify the results that you require, not the order of the operations to retrieve these results.
		- Relational Algebra is **procedural** - We must specify exactly *how* to retrieve results when using relational algebra.
	- ## Relational Algebra Expressions
		- A valid relational algebra expression is built by connecting tables or expressions with defined **unary & binary operators** & their arguments (if applicable).
		- Temporary relations resulting from a relational algebra expression can be used as input to a new relational algebra expression.
		- Expressions in brackets are evaluated first.
		- Relational Algebra operators are either **unary** or **binary**.
	- ## Working With the [RelaX Calculator](https://dbis-uibk.github.io/relax/calc/local/uibk/local/0)
		- There is no standard language for relational algebra like there is for SQL.
		- One University group have developed a calculator that supports a *fairly* command standard.
		- Note that it is Case Sensitive.
		- The RelaX calculator provides a number of datasets with the option of also using your own dataset.
		- ### Loading a Dataset
			- 1. Go to the "Group Editor" tab.
			  2. Copy text from the file on Blackboard and add.
			  3. Then choose "Preview".
			  4. Then choose "Use group in Editor".
			- **Note:** Only stored temporarily.
		- ### Note on Degrees
			- The **degree** of the relation resulting from a selection of a table $R$ is the same as the degree of $R$, i.e., they have the same number of attributes (columns).
			- The operation is **commutative** - i.e., a sequence of selects can be applied in any order.
			- E.g.:
				- $$\sigma_{\text{hours < 20 and pno = 10}}\text{works\_on}$$
				- $$\sigma_{\text{pno = 10 and hours < 20}}\text{works\_on}$$
- # Relational Algebra: Unary Operators
	- Each operation takes one relation or expression as input and gives a new relation as a result.
	- ## Selection Operator ($\sigma$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T16:16:42.201Z
	  card-last-score:: 1
		- Used to **select** certain tuples (rows) from a relation $R$.
		- **Notation:** $\sigma_pR$, where $p$ is the **selection predicate** (i.e., a *condidtion*) and $R$ is a relation / table name.
		- **Note:** The Selection ($\sigma$) operator in relational algebra is **not** the same as the `SELECT` clause in an SQL query.
			- An SQL `SELECT` query could be equivalent to a combination of relational algebra operators, ($\sigma$, $\pi$, or `JOIN`).
		- ### Example (Using Company Schema)
			- Find the projects with pno = 10 and hours worked < 20.
			  background-color:: green
				- $$\sigma_{\text{hours < 20 AND pno = 10}}\text{works\_on}$$
				- Returns the set:
					- {(333445555, 10, 10.0 ), (999887777, 10, 10.0)}
	- ## Projection Operator ($\pi$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T16:20:37.894Z
	  card-last-score:: 1
		- Used to return certain attributes / columns.
		- **Notation:**
			- $$\pi_{A_1, A_2, \cdots, A_k}(R)$$
			- Where $A_1, \cdots, A_k$ are attribute names, $R$ is a relation name.
		- The result is a relation with the $k$ attributes listed in the same order as they appear in the list.
			- Duplicate tuples are removed from the result.
		- **Note:** Commutativity does *not* hold.
		- ### Example (Using Company Schema)
			- List all the department numbers where employees work.
			  background-color:: green
				- $$\pi_\text{dno}\text{employee}$$
				- Returns: {5,4,1}.
	- ## Rename Operators ($\rho$ & $\leftarrow$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T16:23:44.470Z
	  card-last-score:: 1
		- Rename Operation ($\rho$).
		- **Notation:** $\rho_x(E)$, where the result of the expression $E$ is saved with the name $x$.
	- ## Order Operator ($\tau$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-22T00:00:00.000Z
	  card-last-reviewed:: 2022-11-21T13:09:00.157Z
	  card-last-score:: 1
		- Used to **order** by certain columns from a relation $R$.
		- **Notation:** $\tau_{A_1, A_2, \cdots, A_k}R$ where $A_1, A_2, \cdots, A_k$ are attributes with either ASC or DESC.
	- ## Group By Operator ($\gamma$)
		- Used to **group** by certain columns from a relation $R$.
	- ## Aggregate Functions Supported by RelaX
		- (Not part of Relation Algebra),
		- `COUNT(*)`.
		- `COUNT(column)`.
		- `MIN(column)`.
		- `MAX(column)`.
		- `SUM(column)`.
		- `AVG(column)`.
- # Binary Operators
	- General syntax: `(child_expression) function argument (child_argument)`.
	- ## Union Operator ($\cup$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T15:54:46.077Z
	  card-last-score:: 1
		- **Notation:** $(R) \cup (S)$, where $R$ & $S$ are relations.
		- Returns all tuples from $R$ and all tuples from $S$.
		- **Note:** No duplicates will be returned.
	- ## Intersection Operator ($\cap$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T20:25:05.543Z
	  card-last-score:: 1
		- **Notation:** $(R) \cap (S)$, where $R$ & $S$ are relations.
		- Returns all tuples from $R$ that are also in $S$.
	- ## Set Difference ($-$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T20:26:25.449Z
	  card-last-score:: 1
		- **Notation:** $(R) - (S)$ where $R$ & $S$ are relations.
		- Returns tuples that are in relation $R$ but not in $S$.
		- **Note:** $(R) - (S)$ and $(S) - (R)$ are not the same.
	- ## Union Compatibility
		- What is **Union Compatibility**? #card
		  card-last-interval:: -1
		  card-repeats:: 1
		  card-ease-factor:: 2.5
		  card-next-schedule:: 2022-11-15T00:00:00.000Z
		  card-last-reviewed:: 2022-11-14T15:50:22.644Z
		  card-last-score:: 1
			- For union, intersection, & minus, relations must be **union compatible**.
				- That is, schemas of relations must match, i.e., have the same number of attributes and each corresponding attributes have the same domain.
	- ## Cartesian Product Operator ($\times$) (Cross-Join) #card
	  id:: 636a860b-5170-4227-befa-68876a53c856
	  card-last-interval:: 0.98
	  card-repeats:: 2
	  card-ease-factor:: 2.36
	  card-next-schedule:: 2022-11-22T12:05:57.590Z
	  card-last-reviewed:: 2022-11-21T13:05:57.590Z
	  card-last-score:: 3
		- **Notation:** $(R) \times (S)$ where $R$ & $S$ are relations / tables.
		- **Returns:** Tuples comprising the concatenation (combination) of *every tuple* in $R$ with *every tuple* in $S$.
		- **Note:** No condition specified.
		- ### Cartesian Product vs Join #card
		  card-last-interval:: -1
		  card-repeats:: 1
		  card-ease-factor:: 2.5
		  card-next-schedule:: 2022-11-15T00:00:00.000Z
		  card-last-reviewed:: 2022-11-14T16:20:31.096Z
		  card-last-score:: 1
			- The main difference between a Cartesian product operator and a Join operator is that, with a Join, only tuples **satisfying a condition** appear in the result, while in a Cartesian product operator, all combinations of tuples are included in the result.
	- ## Join Operator ($\Join$) #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T16:23:36.847Z
	  card-last-score:: 1
		- The **Join Operator** is a *hybrid* operator - it is a combination of the **Cartesian Product** operator ($\times$) & a **Select** operator ($\sigma$).
		- Tables are joined together based on the **condition** specified.
	- ## Equi & Theta Joins #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-19T00:00:00.000Z
	  card-last-reviewed:: 2022-11-18T18:37:52.403Z
	  card-last-score:: 1
		- **Notation:** $(R_1) \Join p (R_2)$ where $p$ is the **join condition** and $R_1$ & $R_2$ are relations.
		- **Result:** The `JOIN` operation returns all combinations of tuples from relation $R_1$ & relation $R_2$ satisfying the join condition $p$.
		- **Note:** EQUI JOINS use only equality comparisons (`=`) in the join condition $p$.
-
-