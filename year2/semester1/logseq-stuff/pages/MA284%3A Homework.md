- #[[MA284 - Discrete Mathematics]]
-
- # Assignment 1 2022-09-15
	- ### Problem 6
		- A survey of 1,000 employees in a company revealed that 289 like rock music, 325 like pop music, 136 like jazz, 135 like pop and rock music, 58 like jazz and rock, 39 like pop and jazz, and 20 employees like all three.
			- How many employees **do not like** jazz, pop, or rock music?
				- $$ |P \cup R \cup J| = |P| + |R| + |J| - |P \cap R| - |P \cap J| - |R \cap J| + |P \cap R \cap J| \newline= 289 + 325 + 136 - 135 - 58 - 39 + 20 = 538$$
				- $$|(P \cup R \cup J)^C| = |U| - |P \cup R \cup J| = 1,000 - 538 = 462$$
				- 1
			- How many employees like **pop but not jazz**?
				- 325 employees like pop, but only 39 like pop **and** jazz, so the number of employees who like pop but not jazz is $325 - 39 = 286$
	-
	- ### Problem 7
	  collapsed:: true
		- How many 3-element subsets containing the letter A can be formed from the set $\{A,B,C,D,E,F,G\}$?
			- $$|\{A,B,C,D,E,F,G\}| = 7$$
			- Keyword is **subsets** - ^^order doesn't matter, elements can't be repeated.^^
			- ~~Must have A, so only have two choices; the first choice has 6 options, as A is gone, the second has 5, as both A and the previous letter are gone.~~
				- $$6\times 5 \times 1 = 30$$
			- answer isn't 30, 49
			-
			- Possibilities:
				- First Letter is A:
					- 1 choice for first letter - A
					- 6 choices for second letter
					- 5 choices for third letter
					- Total = 1 x 6 x 5 = 30
				- Second Letter is A:
					- 6 choices for first letter
					- 1 choice for second letter - A
					- 5 choices for third letter
				- Third Letter is A:
					- 6 choices for first letter
					- 5 choices for second letter
					- 1 choice for third letter - A
	- ### Problem 8
		- A DNA sequence can be represented as a string of the letters ACTG.
			- (a) How many DNA sequences are exactly 22 letters long?
				-