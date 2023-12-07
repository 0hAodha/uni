- #[[MA284 - Discrete Mathematics]]
- **Previous Topic:** [[Colouring Graphs; Eulerian & Hamiltonian Graphs]]
- **Next Topic:** [[Matrices]]
- **Relevant Slides:** ![MA284-Week11.pdf](../assets/MA284-Week11_1668603812290_0.pdf)
-
- # Trees
	- What is an **acyclic graph** or a **forest**? #card
	  card-last-interval:: 2.8
	  card-repeats:: 1
	  card-ease-factor:: 2.6
	  card-next-schedule:: 2022-11-19T16:18:08.222Z
	  card-last-reviewed:: 2022-11-16T21:18:08.222Z
	  card-last-score:: 5
		- A graph that has no circuits is called **acyclic** or a "forest".
		- It is made up of **trees**.
	- What is a **tree**? #card
	  card-last-interval:: 0.98
	  card-repeats:: 1
	  card-ease-factor:: 2.36
	  card-next-schedule:: 2022-11-17T20:21:51.032Z
	  card-last-reviewed:: 2022-11-16T21:21:51.032Z
	  card-last-score:: 3
		- A **tree** is a connected, acyclic graph.
		- A graph is a tree if and only if there is a **unique path** between any two vertices.
			- If a graph has a unique path between pairs of vertices then it has no cycles, but there is a path between each pair, so it is connected.
		- A tree is a forest.
	- ## If $T$ is a tree, then $e = v - 1$. #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-18T00:00:00.000Z
	  card-last-reviewed:: 2022-11-17T09:45:56.481Z
	  card-last-score:: 1
		- If $T$ is a tree (i.e., a connected acyclic graph) with $v$ vertices, then it has $v-1$ edges.
		- The converse of this statement is also true. ((6374e0df-7be4-4928-bf03-c336e517bbf9))
	- It can be difficult to determine if a very large graph is a tree just by inspection.
		- If we know that it has no cycles, then we need to verify that it is connected. The following result (the converse of the previous one) can be useful:
			- ## If $e = v-1$, then $T$ is a tree. #card
			  id:: 6374e0df-7be4-4928-bf03-c336e517bbf9
			  card-last-interval:: -1
			  card-repeats:: 1
			  card-ease-factor:: 2.5
			  card-next-schedule:: 2022-11-18T00:00:00.000Z
			  card-last-reviewed:: 2022-11-17T09:46:20.887Z
			  card-last-score:: 1
				- If a graph with $v$ vertices has no cycles, and has $e = v-1$ edges, then it is a tree.
- ## Applications
	- What is a **Decision Tree**? #card
	  card-last-interval:: 0.98
	  card-repeats:: 1
	  card-ease-factor:: 2.36
	  card-next-schedule:: 2022-11-18T08:46:46.255Z
	  card-last-reviewed:: 2022-11-17T09:46:46.255Z
	  card-last-score:: 3
		- A **Decision Tree** is a graph where each node represents a possibility, and each branch/edge from that node is a possible outcome.
- # Spanning Trees
	- What is a **Spanning Tree**? #card
	  card-last-interval:: -1
	  card-repeats:: 1
	  card-ease-factor:: 2.5
	  card-next-schedule:: 2022-11-18T00:00:00.000Z
	  card-last-reviewed:: 2022-11-17T09:47:19.672Z
	  card-last-score:: 1
		- Given a (simple) graph $G$, a **spanning tree** of $G$ is a subgraph of $G$ that is a tree, and contains every vertex of $G$.
	- Lots of spanning trees are possible, and there are numerous ways of finding them. Here are two:
		- ## Algorithm 1
			- (i) Identify a cycle in the graph.
			  (ii) Delete an edge in that cycle, taking care not to disconnect the graph.
			  (iii) Keep going until all cycles have been removed.
		- ## Algorithm 2
			- (i) Start with just the vertices of the graph (no edges).
			  (ii) Add an edge from the original graph, as long as it does not form a cycle.
			  (iii) Stop when the graph is connected.
	- For many applications, we need to consider a wider class of graphs: **weighted graphs**.
	- ## Minimum Spanning Trees
		- What is a **minimum spanning tree**? #card
		  card-last-interval:: 2.8
		  card-repeats:: 1
		  card-ease-factor:: 2.6
		  card-next-schedule:: 2022-11-21T13:33:14.273Z
		  card-last-reviewed:: 2022-11-18T18:33:14.274Z
		  card-last-score:: 5
			- A **minimum spanning tree** is a spanning tree with the minimum possible total edge weight.
		- ### Kruskal's Algorithm
			- (i) Start with just the vertices.
			  (ii) Add the edge with the least weight that does not form a cycle.
			  (iii) Keep going until the graph is connected.
		- ### Prim's Algorithm
			- (i) Choose any vertex from the original graph.
			  (ii) Add the edge incident to any vertex in the connected component with least weight that has the least weight and does not create a cycle.
			  (iii) Stop when you reach all the vertices of the original graph.
		-