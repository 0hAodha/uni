# `ipd.py`
## Dependencies
The program doesn't use any dependencies other than Python itself, so as long as you have Python installed, you're good to go.

## Usage
Run the program using `python ipd.py -h` for the following usage information:
```
usage: ipd.py [-h] [-s SIZE] [-g NUM_GENERATIONS] [-a GIVE_UP_AFTER]
              [-i NUM_ITERATIONS] [-p SELECTION_PROPORTION]
              [-c CROSSOVER_RATE] [-m MUTATION_RATE] [-o OUTPUT_FILE]
              [-n NOISE_LEVEL]

Program to evolve strategies for the Iterated Prisoner's Dilemma

options:
  -h, --help            show this help message and exit
  -s, --size SIZE       Initial population size
  -g, --num-generations NUM_GENERATIONS
                        Number of generations
  -a, --give-up-after GIVE_UP_AFTER
                        Number of generations to give up after if best
                        solution has remained unchanged
  -i, --num-iterations NUM_ITERATIONS
                        Number of iterations of the dilemma between two agents
  -p, --selection-proportion SELECTION_PROPORTION
                        The proportion of the population to be selected
                        (survive) on each generation
  -c, --crossover-rate CROSSOVER_RATE
                        Probability of a selected pair of solutions to
                        sexually reproduce
  -m, --mutation-rate MUTATION_RATE
                        Probability of a selected offspring to undergo
                        mutation
  -o, --output-file OUTPUT_FILE
                        File to write TSV results to
  -n, --noise-level NOISE_LEVEL
                        The probability that the opponent's last move will be
                        misrepresented to the agent
```

An example run of the program with default values would simply be `python ipd.py`.
To change the fitness function used, comment / uncomment lines in the list of possible strategies in the `fitness()` function.

