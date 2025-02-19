## Pre-Requisites
- Ensure that Python 3 is installed.
- Install the required dependencies: `pip install matplotlib pandas`.

## `salesman.py`
- Run `python3 salesman.py --help`  for usage information:
```
usage: salesman.py [-h] -i INPUT_FILE [-s SIZE] [-g NUM_GENERATIONS] [-a GIVE_UP_AFTER] [-p SELECTION_PROPORTION] [-c CROSSOVER_RATE] [-m MUTATION_RATE] [-o OUTPUT_FILE] [--quiet] [--grid-search]

Program to solve the travelling salesman problem for a given TSP file using a genetic algorithm.

options:
  -h, --help            show this help message and exit
  -i, --input-file INPUT_FILE
                        Path to input file in TSP format
  -s, --size SIZE       Initial population size
  -g, --num-generations NUM_GENERATIONS
                        Number of generations
  -a, --give-up-after GIVE_UP_AFTER
                        Number of generations to give up after if best solution has remained unchanged
  -p, --selection-proportion SELECTION_PROPORTION
                        The proportion of the population to be selected (survive) on each generation
  -c, --crossover-rate CROSSOVER_RATE
                        Probability of a selected pair of solutions to sexually reproduce
  -m, --mutation-rate MUTATION_RATE
                        Probability of a selected offspring to undergo mutation
  -o, --output-file OUTPUT_FILE
                        File to write TSV results to
  --quiet               Don't print output
  --grid-search         Perform a grid search for optimal population size, crossover rate, & mutation rate
```

Example usages include:
- `python3 salesman.py -i berlin52.tsp`
- `python3 salesman.py -i berlin52.tsp --grid-search`
- `python3 salesman.py -i ../materials/berlin52.tsp  --size 300 --crossover-rate 0.8 --mutation-rate 0.1`

## `plots.py`
- After running `salesman.py` and getting an `output.tsv` file, the results can be plotted using `python3 plots.py output.tsv`.

## `map.py`
- I never got around to adding command-line flags, so to plot a tour using `map.py`, edit the source code to replace the path to the TSP file and the list representing the tour to the desired values.
- Then, `python3 map.py`.
