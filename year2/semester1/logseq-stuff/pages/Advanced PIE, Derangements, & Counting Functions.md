- #[[MA284 - Discrete Mathematics]]
- **Previous Topic:** [[Stars & Bars]]
- **Next Topic:** [[Introduction to Graph Theory]]
- **Relevant Slides:** ![MA284-Week06.pdf](../assets/MA284-Week06_1665576169094_0.pdf)
-
- # Advanced Counting Using PIE
  collapsed:: true
	- The PIE works for larger number of sets than 2 and 3, although it gets a little messy to write down.
		- For 4 sets, we can think of it as:
			- $|A \cup B \cup C \cup D| =$ (the sum of the sizes of each single set) $-$ (the sum of the sizes of each **intersection** of 2 sets) $+$ (the sum of the sizes of each **intersection** of 3 sets) $-$ (the sum of the sizes of the **intersection** of all 4 sets).
		- ## Example
			- How many ways can we distribute 10 slices of pie to 4 kids that such that no kid gets more than 2 slices (and each slice is distributed)? [See [the textbook](https://discrete.openmathbooks.org/dmoi3/sec_advPIE.html) for a more detailed solution.]
				- The answer is obviously 0 - there will be 2 slices leftover after each kid gets the maximum of 2 slices.
				- Without the restriction that nobody gets more than 2 slices, there would be $\binom{13}{3} = 286$ ways of sharing distributing the slices ($10+4-1$ stars & $4-1$ bars).
				- Now, count the number of ways where a child gets more than 2 slices, i.e. some child gets $\geq 3$ slices.
					- $$\binom{4}{1} \binom{7+3}{3} = 4(120) = 480$$
					- (choose one of 4 kids)(number of ways of distributing).
				- Add back in the doubly counted ones, subtracted the triply counted,
				-
					- $$\binom{13}{3} - \binom{4}{1} \binom{10}{3} + \binom{4}{2} \binom{7}{3} - \binom{4}{3} \binom{4}{3} + \binom{4}{4} \binom{1}{3} \\ =  286 - 480 +210 -16 = 0$$
			- ## Example
				- Not all problems have such easy solutions.
				- How many non-negative integer solutions are there to $x_1 + x_2 + x_3 +x_4 +x_5 = 13$ if:
					- 1. There are no restrictions (other than $x_i$ being an **nni**).
					  2. $0\leq x_i \leq 3$ for each $i$.
					- 1. $$\displaystyle \binom{13+4}{4} = \binom{17}{4}$$
					- 2. Idea: All possibilities $-$ "the wrong ones", i.e., count the possibilities where at least one of the $x_i \geq 4$.
						- $\binom{5}{1}$ ways pf choosing the $x_i$ and then number of solutions to $x_1+x_2+x_3+x_4+x_5 = 9$ is $\binom{9+4}{4} = \binom{13}{4}$, i.e. $\binom{5}{1} \binom{13}{4}$.
						- But we have double counted, so number of solutions with two $x_i \ geq 4$ is $\binom{5}{2}$ choices and $x_1+x_2+x_3+x_4+x_5 = 5$ has $\binom{5+4}{4} = \binom{9}{4}$ solutions.
						- Answer: $\displaystyle \binom{17}{4} - \binom{5}{1} \binom{13}{4} + \binom{5}{2}\binom{9}{4} - \binom{5}{3}\binom{5}{4} +0$.
						-
- # Derangements
	- What is a **derangement**? #card
	  card-last-interval:: 3.45
	  card-repeats:: 2
	  card-ease-factor:: 2.46
	  card-next-schedule:: 2022-11-20T19:47:44.301Z
	  card-last-reviewed:: 2022-11-17T09:47:44.301Z
	  card-last-score:: 5
		- A **derangement** is a permutation where no element is left in its original place, everything is moved.
	- ## Example - Derangements of 4 Letters $\text{STARS}$.
		- Let $D_n$ be the number of *derangements* of $n$ objects.
		- First, we will work out the formulae for $D_1$, $D_2$, $D_3$, & $D_4$.
			- $$D_1 = 0,\ D_2 = 1,\ D_3 = 2,\ D_4 = 9$$
		- We derive a formula using PIE.
		- We know that there are $4!$ permutations. Which ones are **not** derangements?
			- Suppose that one item (at least) is left in place.
				- There are $$\displaystyle \binom{4}{1} \cdot 3!$$ such permutations.
					- (choose one item to not change from four)(number of ways of permutating the other items).
					- However, some of these will be counted twice.
						- So, by PIE, the answer is
							- $$D_4 = 4!  - \binom{4}{1}3! + \binom{4}{2}2!-\binom{4}{3}1!+\binom{4}{4}0!$$
							- $$D_4 = 4! - \frac{4!3!}{1!3!} +\frac{4!2!}{2!2!}-\frac{4!1!}{3!1!} + \frac{4!0!}{4!0!}$$
							- $$D_4 = 4![1-\frac{1}{1!}+\frac{1}{2!}-\frac{1}{3!}+\frac{1}{4!}] = 9$$
		- In general, the formula for $D_n$, the number of derangements of $n$ objects is
			- $$D_n = n!(1-\frac{1}{1!}+\frac{1}{2!}-\frac{1}{3!}+ \dots + (-1)^n \frac{1}{n!})$$
		- Note that the series expansion for e^x is
			- $$e^x = 1 + \frac{x}{1!} +\frac{x^2}{2!}+\frac{x^3}{3!} + \dots$$
			- So $$\displaystyle e^{-1} = 1 - \frac{1}{1!} + \frac{1}{2!}-\frac{1}{3!}+ \dots$$
				- So $$\displaystyle \lim_{n \to \infty} \frac{D_n}{n!} = e^{-1} \approx 0.36787$$
- # Counting with Repetitions
	- What is a **Multinomial Coefficient**? #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T20:07:58.830Z
	  card-last-score:: 1
		- The number of different permutations of $n$ objects, where there are $n_1$ indistinguishable objects of Type 1, $n_2$ indistinguishable objects of Type 2, ..., and $n_k$ indistinguishable objects of Type $k$, is
			- $$\frac{n!}{(n_1!)(n_2!) \dots (n_k!)}$$
	- ## Example
		- How many "words" can we make from the letters in the set $\{R,O,S,C,O,M,M,O,N\}$.
			- If somehow the three $O$s were all distinguishable, and the two $M$s were distinguishable, the answer would be $9!$.
		- But, since we can't distinguish the identical letters,
			- Let's choose which of the 9 positions in which we place the three $O$s.
				- This can be done in $$\displaystyle \binom{9}{3}$$ ways.
			- Now, let's choose which of the remaining 6 positions in which we place the two $M$s.
				- This can be done in $$\displaystyle \binom{6}{2}$$ ways.
			- Finally, let's choose where to replace the remaining 4 letters.
				- This can be done in $$4!$$ ways.
			- By the Multiplicative Principle, the answer is
				- $$\binom{9}{3}\binom{6}{2}4! = \frac{9!}{3!6!} \frac{6!}{2!}{4!} 4! = \frac{9!}{3!2!}$$
- # Example (MA284 Semester 1 Exam, 2014/2015)
	- **(i) Find the number of different arrangements of the letters in the place name `WOLLONGONG`.**
		- `OOOLLNNGGW`
		- $$\frac{10!}{3!2!2!2!1!} = 75600$$
	- **(ii) How many of these arrangements start with three `O`s?**
		- `OOO` (one way) and 7 others.
		- $$\frac{7!}{2!2!2!} = 630$$
	- **(ii) How many contain the two `G`s consecutively?**
		- Treat `GG` as a single letter and permute 9 letters.
		- $$\frac{9!}{3!2!2!1!} = 15120$$
	- **(iv) How many *do not* contain the two `G`s consecutively?**
		- Use **(i)** - **(iv)**.
		- $$75600 - 15120 = 60480$$
- # Counting Functions
	- Recall that $f: A \rightarrow B$ is a **function** that maps every element of the set $A$ onto some element of set $B$.
		- We call $A$ the **domain** & $B$ the **codomain**.
		- Each element of $A$ gets mapped to exactly one element of $B$.
	- What does it mean if $a$ is the **image** of $b$? #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.36
	  card-next-schedule:: 2022-11-23T00:00:00.000Z
	  card-last-reviewed:: 2022-11-22T13:40:10.499Z
	  card-last-score:: 1
		- If $f(a) = b$ where $a \in A$ and $b \in B$, we say that "the **image** of $a$ is $b$", or, equivalently, "$b$ is the **image** of $a$".
	- What is a **surjective** function (surjection)? #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T20:13:44.812Z
	  card-last-score:: 1
		- For some function $f: A \rightarrow B$, if every element of $B$ is the image of some element $A$, we say that the function is **surjective** (also called "**onto**").
	- What is an **injective** function (injection)? #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T20:16:06.397Z
	  card-last-score:: 1
		- For some function $f: A \rightarrow B$, if no two elements of $A$ have the same image in $B$, we say that the function is **injective** (also called "one-to-one").
	- What is a **bijective** function (bijection)? #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.7
	  card-next-schedule:: 2022-11-18T20:09:58.766Z
	  card-last-reviewed:: 2022-11-14T20:09:58.766Z
	  card-last-score:: 5
		- The function $f: A \rightarrow B$ is a **bijection** if it is both **surjective** & **injective**.
			- Then $f$ defines a **one-to-one correspondence** between $A$ & $B$
	- ## Examples
		- **Let** $A$ **&** $B$ **be finite sets. How many functions** $f: A \rightarrow B$ **are there?**
			- We can use ((6336be87-7dea-4ba3-b7d0-c77a73bae948)) to deduce that there are in total $|B|^{|A|}$ functions from $A$ to $B$.
		- **How many functions** $f: A\{1,2,3,4,5,6,7,8\} \rightarrow \{1,2,3,4,5,6,7,8\}$ **are bijective**?
			- Remember what it means for a function to be **bijective:** ^^each element in the codomain must be the image of **exactly one** element of the domain.^^
			- What we are really doing is just rearranging the elements of the codomain, so we are defining a **permutation** of 8 elements.
				- Therefore, the answer to our question is 8!.
			- More generally, there are $n!$ bijections of the set $\{1,2,\cdots, n\}$ onto itself.
			- [[2022年10月19日]]
			-