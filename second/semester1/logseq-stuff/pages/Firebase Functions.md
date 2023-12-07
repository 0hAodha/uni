- #[[CT216 - Software Engineering I]]
- **Previous Topic:** [[Introduction to NodeJS]]
- **Next Topic:** [[Introduction to Firestore]]
- **Relevant Slides:** ![Week 10 - Firebase functions, Callbacks, Creating our first function.pdf](../assets/Week_10_-_Firebase_functions,_Callbacks,_Creating_our_first_function_1668085262844_0.pdf)
-
- **Firebase Functions** is a compute service that lets you run code without provisioning or managing servers.
	- Similar to AWS Lambda, Azure Functions, etc.
		- It runs your code only when needed and scales automatically, from a few requests per day to thousands per second.
		- You pay only for the compute time you consume - there is no charge when your code is not running.
- # HTTP Requests
	- ## HTTP Verbs
		- `GET` and `POST` are HTTP request methods to transfer data from client to server.
			- Both can be used to send requests & receive responses.
		- `PUT` is used to update / replace, `DELETE` is used to delete, & `PATCH` is used to partially update / modify.
		- ### `GET`
			- `GET` is designed to request data from a specified resource.
			- `GET` requests can be cached.
			- `GET` requests remain in the browser history (you can go back).
			- `GET` can't be used to send binary data, like images or word documents to the server.
			- `GET` requests can be bookmarked.
			- `GET`requests have length restrictions.
			- `GET` requests should only be used to retrieve data.
			- Using `GET`, data can be sent to the server by adding name=value pairs at the end of the URL, i.e., Querystring.
		- ### `POST`
			- `POST` is designed to submit data to the specified resource.
			- `POST` requests are never cached.
			- `POST` requests do not remain in the browser history.
			- `POST` requests cannot be bookmarked.
			- `POST` requests have no restrictions on data length.
			- The `POST` method can be used to send ASCII as well as binary data.
			- Form data is often sent to the server via a `POST` request.
		- ### `GET` vs `POST`
			- Use `GET` if you are requesting a resource.
				- You may need to send some data to get the correct response back, but in general the idea is to `GET` a resource.
			- Use `POST` if you want to send data to the server.
	- ## Request Structure #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-15T00:00:00.000Z
	  card-last-reviewed:: 2022-11-14T15:50:44.830Z
	  card-last-score:: 1
		- All HTTP requests have three main parts.
		- ### Request Line
			- HTTP Method (`GET`, `POST`, etc.).
			- URL - the address of the resource that is being requested.
			- HTTP version.
		- ### Headers
			- Additional information passed between the browser & the server, i.e., cookies, browser version, OS version, auth tokens, content-type.
		- ### Message Body
			- Client & server use the message body to transmit data back & forth between each other.
				- `POST` requests will usually have data in the body.
				- `GET` requests leave the message data empty.
	- ## Postman Client
		- When writing backend APIs, it's often necessary to test it quickly.
			- You don't want to have to write a client-side request to test each API.
			- Sometimes, you may even want to pass in values which would take even longer to code up.
			- Postman can help.
		- Postman is good for testing APIs without having to write client-side code to make the requests.
			- It will work for all request methods, i.e., `GET`, `POST`, `PUT`. etc.
			- You can code the backend, independent of the frontend.
- # JSON
	- What is **JSON**? #card
	  card-last-interval:: 2.8
	  card-repeats:: 1
	  card-ease-factor:: 2.6
	  card-next-schedule:: 2022-11-17T11:23:52.503Z
	  card-last-reviewed:: 2022-11-14T16:23:52.503Z
	  card-last-score:: 5
		- **JavaScript Object Notation (JSON)** is an open, human & machine readable standard that facilitates data interchange.
		- Along with XML, JSON is the main data interchange format on the web.
		- Firestore uses JSON documents to store records of information.
		-