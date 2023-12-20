- #[[CT216 - Software Engineering I]]
- **Previous Topic:** [[Bootstrap CSS]]
- **Next Topic:** [[Introduction to NodeJS]]
- **Relevant Slides:** ![Week 8 - JavaScript - Functions, Events.pdf](../assets/Week_8_-_JavaScript_-_Functions,_Events_1668007671511_0.pdf)
-
- # Functions
  collapsed:: true
	- A function can be named or it can be anonymous.
	- ## ES6 Arrow Functions
		- Arrow functions are more concise - a developer can achieve the same functionality with fewer lines of code.
		- ```javascript
		  // ES6 Arrow Function
		  (param1, param2) =>
		  {
		    return param1 * param2;
		  }
		  
		  // ES5 Traditional Function
		  function(param1, param2)
		  {
		    return param1 * param2;
		  }
		  ```
	- ## Assigning a Function to a Variable
		- ```javascript
		  let multiply = (param1, param2) => {
		    return param1 * param2;
		  }
		  multiply(2,3)
		  ```
- # Events
	- An **event** is an action that can be responded to by JavaScript.
	- Every element on a page has certain events which can trigger some code.
		- We can identify when a user clicks a button with the `onClick` event.
	- ## Event Examples
		- ### Mouse Events
			- `onClick`: Triggered when the mouse clicks on an element.
			- `onMouseDown`: Triggered when the mouse button is pressed.
			- `onMouseUp`: Triggered when the mouse button is released.
			- `onMouseOver`: Triggered for an element when the mouse cursor is moved over that element.
			- `onMouseOut`: Triggered for an element when the mouse cursor is moved away from that element.
		- ### Selecting & De-Selecting Elements
			- `onFocus`: Triggered when an element gets focus.
			- `onBlur`: Triggered when an element loses focus.
			- `onChange`: Triggered when the content of an element changes.
		- ### Keyboard Events
			- `onKeyDown`: Triggered when a keyboard key is pressed.
			- `onKeyUp`: Triggered when a keyboard key is released.
			- `onKeyPress`: Triggered when a keyboard key is pressed or held.
			- `onSelect`: Triggered when text is selected.
			-