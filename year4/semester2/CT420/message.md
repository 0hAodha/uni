#### Scheduling Algorithms
- A schedule is feasible if all the tasks start after their release time and complete before their deadlines
- Rate-Monotonic Scheduling (RM)
	- A model for optimal static priority scheduling algorithm
	- Not straight-forward when it comes to guarantee the feasibility of a task schedule
	- Use this algorithm if task priorities **do not** change
	- The scheduling decision is made when the current task execution is complete and a new task is released
	- The task utilisation is execution time divided by period
		- The CPU utilisation for the schedule is just the sum of all of these
	- The smallest period is the highest priority for all released tasks
	- Tasks are pre-empted if a higher-priority task is released
	- A general schedulability test for RM is $$
	U \leq n \left( 2^{\frac{1}{n}} - 1 \right)
	$$
		where $n$ is the number of tasks
		- If this is true, a feasible schedule is guaranteed, no need for further analysis
		- Otherwise, you need to actually check feasibility
		- This can be done by checking $$
	W_{i}(t) = \sum_{j = 1}^{i} e_{j} \left( \frac{t}{p_{j}} \right) \leq t
	$$
			for task interval $t$ and number of tasks $i$
		- $\frac{t}{p_{j}}$ is always **rounded up**
		- Check each of the points to see which tasks cause infeasibility which may include repeats of periods
			- In other words, you can check whether 2 tasks will work in the period for 
		- The schedule is feasible if this is true
- Earliest Deadline First (EDF)
	- Use this algorithm if task priorities **do** change over time
	- Also preemptable
	- The tasks with the earliest absolute deadline are given highest priority
	- Priorities are re-evaluated when tasks are released or completed
	- This is an optimal single-processor scheduling algorithm
	- If $U \leq 1$ then the task set is schedulable



#### Scheduling Example from Paper
| t   | e   | p   |
| --- | --- | --- |
| 1   | 10  | 50  |
| 2   | 20  | 100 |
| 3   | 30  | 150 |
| 4   | 40  | 200 |
**CE**
Must execute tasks every 50 ms

1 + 4 = 50 so we need these to be on their own every time

2 + 3 = 50 so these also need to be on their own

is this even possible?

it will have overruns - 4 has to be split into 2 subtasks

| t   | e   | p   |
| --- | --- | --- |
| 1   | 10  | 50  |
| 2   | 20  | 100 |
| 3   | 30  | 150 |
| 4a  | 20  | 200 |
| 4b  | 20  | 200 |


1 has to be called every time since the period is 50


| time | tasks    |     |
| ---- | -------- | --- |
| 0    | 1, 2, 4a |     |
| 50   | 1, 4b    |     |
| 100  | 1, 2     |     |
| 150  | 1, 3     |     |
| 200  | 1, 2, 4a |     |
| 250  | 1, 4b    |     |
| 300  | 1, 2     |     |
| 350  | 1, 3     |     |



**RM**
Total Utilisation: $$
\sum_{i = 1}^{n} u_{i}:u_{i} = \frac{e_{i}}{p_{i}}
$$
$$
0.2 + 0.2 + 0.2 + 0.2 = 0.8
$$
$$
0.8 \leq n(2^{1/n} - 1)
$$
$$
0.8 > 0.76
$$
$$
W_{4} = \{50, 100, 150, 200\}
$$
$$
W_{4}(t) = \sum_{j = 1}^{4} e_{j} \left( \frac{t}{p_{j}} \right)
$$
$$
= 10(4) + 20(2) + 30(2) + 40(1)
$$
$$
= 180 < 200
$$
this is feasible

| time | task running | tasks available |     |
| ---- | ------------ | --------------- | --- |
| 0    | 1            | 1, 2, 3, 4      |     |
| 10   | 2            | 2, 3, 4         |     |
| 20   | 2            | 2, 3, 4         |     |
| 30   | 3            | 3, 4            |     |
| 40   | 3            | 3, 4            |     |
| 50   | 1            | 1, 3, 4         |     |
| 60   | 3            | 3, 4            |     |
| 70   | 4            | 4               |     |
| 80   | 4            | 4               |     |
| 90   | 4            | 4               |     |
| 100  | 1            | 1, 2, 4         |     |
| 110  | 2            | 2, 4            |     |
| 120  | 2            | 2, 4            |     |
| 130  | 4            | 4               |     |
| 140  |              |                 |     |
| 150  | 1            | 1, 3            |     |
| 160  | 3            | 3               |     |
| 170  | 3            | 3               |     |
| 180  | 3            | 3               |     |
| 190  |              |                 |     |
| 200  | 1            | 1, 2, 4         |     |
| 210  | 2            | 2, 4            |     |
| 220  | 2            | 2, 4            |     |
| 230  | 4            | 4               |     |
| 240  | 4            | 4               |     |
| 250  | 1            | 1, 4            |     |
| 260  | 4            | 4               |     |
| 270  | 4            | 4               |     |
| 280  |              |                 |     |
| 290  |              |                 |     |
| 300  | 1            | 1, 3            |     |


**EDF**
$$
0.8 < 1
$$
this is feasible

| time | task running | tasks available |     |
| ---- | ------------ | --------------- | --- |
| 0    | 1            | 1, 2, 3, 4      |     |
| 10   | 2            | 2, 3, 4         |     |
| 20   | 2            | 2, 3, 4         |     |
| 30   | 3            | 3, 4            |     |
| 40   | 3            | 3, 4            |     |
| 50   | 1            | 1, 3, 4         |     |
| 60   | 3            | 3, 4            |     |
| 70   | 4            | 4               |     |
| 80   | 4            | 4               |     |
| 90   | 4            | 4               |     |
| 100  | 1            | 1, 2, 4         |     |
| 110  | 2            | 2, 4            |     |
| 120  | 2            | 2, 4            |     |
| 130  | 4            | 4               |     |
| 140  |              |                 |     |
| 150  | 1            | 1, 3            |     |
| 160  | 3            | 3               |     |
| 170  | 3            | 3               |     |
| 180  | 3            | 3               |     |
| 190  |              |                 |     |
| 200  | 1            | 1, 2, 4         |     |
| 210  | 2            | 2, 4            |     |
| 220  | 2            | 2, 4            |     |
| 230  | 4            | 4               |     |
| 240  | 4            | 4               |     |
| 250  | 1            | 1, 4            |     |
| 260  | 4            | 4               |     |
| 270  | 4            | 4               |     |
| 280  |              |                 |     |
| 290  |              |                 |     |
| 300  | 1            | 1, 3            |     |
