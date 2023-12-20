title:: Using R as a Calculator

- #[[ST2001 Labs]]
- **Previous Topic:** null
- **Next Topic:** [[Describing Data in R]]
- No relevant slides
-
- ## Basic Algebra in R
	- ### Addition #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:44:00.469Z
	  card-last-reviewed:: 2022-11-14T16:44:00.470Z
	  card-last-score:: 5
		- ```R
		  # to add numbers in R, simply use "+"
		  2+2
		  ```
		- Output:
			- ```R
			  [1] 4
			  ```
	- ### Subtraction #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:44:48.506Z
	  card-last-reviewed:: 2022-11-14T16:44:48.506Z
	  card-last-score:: 5
		- ```R
		  # to subtract numbers in R, simply use "-"
		  4-2
		  ```
		- Output:
			- ```R
			  [1] 2
			  ```
	- ### Multiplication #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:46:18.737Z
	  card-last-reviewed:: 2022-11-14T16:46:18.738Z
	  card-last-score:: 5
		- ```R
		  # to multiply numbers in R, simply use "*"
		  5*2	
		  ```
		- Output:
			- ```R
			  [1] 10
			  ```
	- ### Division #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-11-22T23:24:36.469Z
	  card-last-reviewed:: 2022-10-20T08:24:36.470Z
	  card-last-score:: 5
		- ```R
		  # to divide numbers in R, simply use "/"
		  10/5
		  ```
		- Output:
			- ```R
			  [1] 2
			  ```
	- ### Exponents #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:51:39.910Z
	  card-last-reviewed:: 2022-11-14T16:51:39.911Z
	  card-last-score:: 5
		- ```R
		  # use "^" to raise a number to a power
		  3^2
		  3^{-1} # use curly braces
		  ```
		- Output:
			- ```R
			  [1] 9
			  [1] 0.3333333
			  ```
	- ### Square Roots #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:37:35.704Z
	  card-last-reviewed:: 2022-11-14T16:37:35.705Z
	  card-last-score:: 5
		- ```R
		  # use the function "sqrt()" to get the square root of a number in R
		  sqrt(16)
		  ```
		- Output:
			- ```R
			  [1] 4
			  ```
		-
	- ### Modulus #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:38:53.453Z
	  card-last-reviewed:: 2022-11-14T16:38:53.454Z
	  card-last-score:: 5
		- ```R
		  # use "%%" to get the modulus
		  19%%6
		  ```
			- Output:
				- ```R
				  [1] 1
				  ```
- ## Rounding in R
	- ### Absolute Value #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:44:02.326Z
	  card-last-reviewed:: 2022-11-14T16:44:02.327Z
	  card-last-score:: 5
		- ```R
		  # use "abs()" to get absolute value in R
		  abs(-1)
		  ```
			- Output:
				- ```R
				  [1] 1
				  ```
	- ### Rounding #card
	  card-last-interval:: 29.26
	  card-repeats:: 4
	  card-ease-factor:: 2.66
	  card-next-schedule:: 2022-12-13T22:41:14.492Z
	  card-last-reviewed:: 2022-11-14T16:41:14.492Z
	  card-last-score:: 5
		- The function `round()` in R goes not necessarily do what you would expect when rounding numbers ending in **.5** - ^^it rounds to the nearest **even** number.^^
			- If you always round up numbers ending in .5, then you are causing an upwards bias.
			- The rounding to even numbers will tend to average out at a zero bias, as 50% go up and 50% go down.
		- ```R
		  # use "round()" to round
		  round(1.5)
		  round(0.5)
		  round(0.7)
		  ```
			- Output:
				- ```R
				  [1] 2
				  [1] 0
				  [1] 1
				  ```
- ## $\pi$ in R #card
  card-last-interval:: 33.64
  card-repeats:: 4
  card-ease-factor:: 2.9
  card-next-schedule:: 2022-12-18T07:38:10.483Z
  card-last-reviewed:: 2022-11-14T16:38:10.484Z
  card-last-score:: 5
	- ```R
	  # to get pi in R, simply use the in-built constant "pi"
	  pi
	  ```
	- Output:
		- ```R
		  [1] 3.141593
		  ```
- ## Trigonometric Functions in R
	- ### Sine in R #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-15T02:31:07.981Z
	  card-last-reviewed:: 2022-11-11T11:31:07.981Z
	  card-last-score:: 5
		- ```R
		  # to get the sine of a number in R, use the function "sin()"
		  sin(0.5 * pi)
		  sin(pi)
		  ```
		- Output:
			- ```R
			  [1] 1
			  [1] 1.224647e-16
			  ```
			- ^^**Note:**^^ $1.224606e-16 \approx 0$. Due to the way computers store numbers, decimals are often slightly off, so $sine(\pi) \ne 0$ even though it should, of course, be equal to zero. Be careful of this!
	- ### Cosine in R #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:45:11.108Z
	  card-last-reviewed:: 2022-11-14T16:45:11.109Z
	  card-last-score:: 5
		- ```R
		  # use "cos()" to get cosine
		  cos(0)
		  ```
			- Output:
				- ```R
				  [1] 1
				  ```
	- ### Tangent in R #card
	  card-last-interval:: 33.64
	  card-repeats:: 4
	  card-ease-factor:: 2.9
	  card-next-schedule:: 2022-12-18T07:51:39.012Z
	  card-last-reviewed:: 2022-11-14T16:51:39.012Z
	  card-last-score:: 5
		- ```R
		  # use "tan()" to get tangent
		  tan(0)
		  ```
		- Output:
			- ```R
			  [1] 0
			  ```
- ## Logarithms in R
	- ### Natural Log #card
	  card-last-interval:: 28.3
	  card-repeats:: 4
	  card-ease-factor:: 2.66
	  card-next-schedule:: 2022-12-07T19:44:02.657Z
	  card-last-reviewed:: 2022-11-09T12:44:02.657Z
	  card-last-score:: 3
		- ```R
		  log(1)
		  ```
		- Output:
			- ```R`
			  [1] 0
			  ```
	- ### Logs to a Given Base #card
	  card-last-interval:: 4
	  card-repeats:: 2
	  card-ease-factor:: 2.66
	  card-next-schedule:: 2022-11-22T18:34:16.626Z
	  card-last-reviewed:: 2022-11-18T18:34:16.626Z
	  card-last-score:: 5
		- ```R
		  # log<base>()
		  log10(100)
		  ```
		- Output:
			- ```R
			  [1] 2
			  ```