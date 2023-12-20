- #[[MA284 - Discrete Mathematics]]
- **Previous Topic:** [[Counting]]
- **Next Topic:**  [[Binomial Coefficients]]
- **Relevant Slides:** ![Week02.pdf](../assets/Week02_1663097329077_0.pdf)
-
- (Aside) **Notation for Complement:** $A^C$ for some set $A$.
-
- ## Counting with Sets
	- ### Additive Principle for Sets
		- #### Additive Principle in terms of "events"
			- If Event $A$ can occur $m$ ways, and Event $B$ can occur $n$ (disjoint / independent) ways, then event "$A$ *or* $B$" can occur in $m+n$ ways.
			- But, an "event" can just be expressed as ^^selecting an element of a set.^^
				- Saying "Event $A$ can occur $m$ ways" is ^^the same^^ as saying "$|A|=m$".
				- Saying "Event $B$ can occur $n$ ways is the same as saying "$|B|=n$".
				- If events $A$ and $B$ are **disjoint** / independent, that means that $|A \cap B| = 0$, or, equivalently , $A \cap B = \emptyset$.
			-
		- What is the **Additive Principle for Sets**? #card
		  card-last-interval:: -1
		  card-repeats:: 1
		  card-ease-factor:: 2.32
		  card-next-schedule:: 2022-11-15T00:00:00.000Z
		  card-last-reviewed:: 2022-11-14T20:02:08.655Z
		  card-last-score:: 1
			- Given two sets $A$ and $B$ with $|A \cap B| = 0$, then:
			- $$|A \cup B| = |A| + |B|$$
	- ### Multiplicative Principle for Sets
		- What is the **Cartesian Product** of two sets? #card
		  card-last-interval:: 11.04
		  card-repeats:: 3
		  card-ease-factor:: 2.76
		  card-next-schedule:: 2022-11-25T16:36:56.463Z
		  card-last-reviewed:: 2022-11-14T16:36:56.463Z
		  card-last-score:: 5
			- The **Cartesian Product** of two sets, $A$ and $B$ is:
				- $$A \times B = \{(x,y) : x \in A \text{ and } y \in B \}$$
			- This is the set of pairs where the first term in each pair comes from $A$, **and** the second term in each pair comes from $B$.
		- #### Multiplicative Principle in terms of "events"
			- If event $A$ can occur $m$ ways, and each possibility allows for $B$ to occur in $n$ (disjoint) ways, then event "$A$ **and** $B$" can occur in $m \times n$ ways.
		- What is the **Multiplicative Principle for Sets**? #card
		  card-last-interval:: 33.64
		  card-repeats:: 4
		  card-ease-factor:: 2.9
		  card-next-schedule:: 2022-12-18T11:00:28.884Z
		  card-last-reviewed:: 2022-11-14T20:00:28.884Z
		  card-last-score:: 5
			- Given two sets, $A$ and $B$:
				- $$|A \times B| = |A| \cdot |B|$$
			- This extends to 3 or more sets in the obvious way.
- ## The Principle of Inclusion & Exclusion (PIE)
	- What is the **Principle of Inclusion & Exclusion**? #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T11:01:03.942Z
	  card-last-reviewed:: 2022-11-14T20:01:03.942Z
	  card-last-score:: 5
		- For any two finite sets $A$ and $B$:
			- $$|A \cup B| = |A| + |B| - |A \cap B|$$
		- This also extends to larger numbers of sets, for example:
			- For any 3 finite sets $A$, $B$, and $C$:
				- $$|A \cup B \cup C| = |A| + |B| + |C| - |A \cap B| - |A \cap C| - |B \cap C| + |A \cap B \cap C |$$
- ## Subsets & Power Sets
	- What is a **power set**? #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.66
	  card-next-schedule:: 2022-11-27T12:19:42.366Z
	  card-last-reviewed:: 2022-11-23T12:19:42.366Z
	  card-last-score:: 5
		- The **power set** of a set $A$, denoted by $P(A)$, is the ^^set of all subsets^^ of $A$, ^^including the empty set^^ ($\emptyset$).
	-