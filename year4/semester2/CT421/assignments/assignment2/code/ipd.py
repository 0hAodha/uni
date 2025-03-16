#!/usr/bin/python3

import argparse
import random

# Each strategy is defined as follows:
    # [first move, reaction to defection, reaction to co-operation]
    # where 0 is defection and 1 is co-operation
# I don't actually know the names for all of these strategies so I'm going to make some up:
strategies = [
    [0, 0, 0],  # Always defect.
    [0, 0, 1],  # Grim tit-for-tat.
    [0, 1, 0],  # Grim opposite day: defect at first, then do opposite of what opponent did last.
    [0, 1, 1],  # Self-sabotage: defect at first, then always co-operate.
    [1, 0, 0],  # Feint co-operation, then always defect.
    [1, 0, 1],  # Tit-for-tat.
    [1, 1, 0],  # Opposite day: co-operate at first, then do opposite of what opponent did last.
    [1, 1, 1]   # Always co-operate.
]

def initialise_population(size):
    """
    Initialises a population of strategies for the Iterated Prisoner's Dilemma.

    Args:
        size (int): The size of the population

    Returns:
        population (list): A list of strategies
    """

    # Since there are only 8 possible strategies, to initialise the population just perform a random over-sampling of the space.
    return random.choices(strategies, k=size)


def coevolve(agent1, agent2, num_iterations):
    """
    Play the Iterated Prisoner's Dilemma with two agents a specified number of times, and return each agent's score. 

    Args:
        agent1 (list): the strategy of agent1.
        agent2 (list): the strategy of agent2.
        iterations (int): the number of iterations to play.

    Returns:
        fitness1 (int): the score obtained by agent1.
        fitness2 (int): the score obtained by agent2.
    """

    fitness1 = 0
    fitness2 = 0

    agent1_last_move = None
    agent2_last_move = None

    for iteration in range(num_iterations):
        if (iteration == 0):
            agent1_move = agent1[0]
            agent2_move = agent2[0]
        else:
            # Set an agent's move to its reaction to co-operation if the other agent's last move was co-operation (1), else set it to its reaction to defection.
            agent1_move = agent1[2] if agent2_last_move else agent1[1]
            agent2_move = agent2[2] if agent1_last_move else agent2[1]

        match (agent1_move, agent2_move):
            case (0, 0):
                fitness1 += 1
                fitness2 += 1
            case (0, 1):
                fitness1 += 5
            case (1, 0):
                fitness2 += 5
            case (1, 1):
                fitness1 += 3
                fitness2 += 3

    return fitness1, fitness2


def fitness(agent, num_iterations):
    """
    Play the Iterated Prisoner's Dilemma against a number of fixed strategies and return its score.

    Args:
        agent1 (list): the strategy of agent1.
        iterations (int): the number of iterations to play.

    Returns:
        fitness1 (int): the score obtained by agent1.
    """

    fitness = 0

    fixed_strategies = [
        [0, 0, 0],  # Always defect.
        # [0, 0, 1],  # Grim tit-for-tat.
        # [0, 1, 0],  # Grim opposite day: defect at first, then do opposite of what opponent did last.
        # [0, 1, 1],  # Self-sabotage: defect at first, then always co-operate.
        # [1, 0, 0],  # Feint co-operation, then always defect.
        [1, 0, 1],  # Tit-for-tat.
        # [1, 1, 0],  # Opposite day: co-operate at first, then do opposite of what opponent did last.
        [1, 1, 1]   # Always co-operate.
    ]

    for fixed_strategy in fixed_strategies:
        agent_last_move = None
        fixed_strategy_last_move = None

        for iteration in range(num_iterations):
            if (iteration == 0):
                agent_move = agent[0]
                fixed_strategy_move = fixed_strategy[0]
            else:
                # Set an agent's move to its reaction to co-operation if the other agent's last move was co-operation (1), else set it to its reaction to defection.
                agent_move = agent[2] if fixed_strategy_last_move else agent[1]
                fixed_strategy_move = fixed_strategy[2] if agent_last_move else fixed_strategy[1]

            agent_last_move = agent_move
            fixed_strategy_last_move = fixed_strategy_move

            match (agent_move, fixed_strategy_move):
                case (0, 0):
                    fitness += 1

                case (0, 1):
                    fitness += 5

                case (1, 0):
                    fitness += 0

                case (1, 1):
                    fitness += 3

    return fitness


def list_fitnesses(population, num_iterations):
    """
    Calculate the fitness of each agent in a population.

    Args:
        population (list): the population of strategies.
        iterations (int): the number of iterations to play.

    Returns:
        fitnesses (list): the fitness of each agent.
    """

    fitnesses = []

    for agent in population:
        fitnesses.append(fitness(agent, num_iterations))

    return fitnesses

def get_best(population, fitnesses, generation):
    """
    Get the best agent in a population, given a list of fitnesses.

    Args:
        population (list): the population of strategies.
        fitnesses (list): the fitness of each agent.

    Returns:
        best_agent (list): the best agent.
    """

    best_index = fitnesses.index(max(fitnesses))
    best_agent = {
        "strategy": list(population[best_index]),
        "fitness": fitnesses[best_index], 
        "generation": generation
    }

    return best_agent

def tournament_selection(population, fitnesses, num_survivors, tournament_size=3):
    """
    Select agents from a population based on their fitness.

    Args:
        population (list): the population of strategies.
        fitnesses (list): the fitness of each agent.
        num_survivors (int): the number of agents to select.

    Returns:
        survivors (list): the selected agents.
    """

    survivors = []

    for _ in range(num_survivors):
        tournament = random.sample(list(zip(population, fitnesses)), tournament_size)
        winner = max(tournament, key=lambda agent: agent[1])
        survivors.append(winner[0])

    return survivors

def crossover(parents, crossover_rate, num_offspring):
    """
    Perform single-point crossover on selected parents.

    Args:
        parents (list): List of selected strategies.
        crossover_rate (float): Probability of crossover occurring.
        num_offspring (int): Number of offspring to generate.

    Returns:
        offspring (list): List of new strategies.
    """
    offspring = []

    while len(offspring) < num_offspring:
        if random.random() < crossover_rate:
            p1, p2 = random.sample(parents, 2)

            crossover_point = random.randint(1, 2)

            child1 = p1[:crossover_point] + p2[crossover_point:]
            child2 = p2[:crossover_point] + p1[crossover_point:]

            offspring.extend([child1, child2])
        else:
            offspring.append(random.choice(parents))

    return offspring[:num_offspring]


def mutate(offspring, mutation_rate):
    """
    Perform bit-flip mutation on offspring.

    Args:
        offspring (list): List of offspring strategies.
        mutation_rate (float): Probability of mutation occurring per individual.

    Returns:
        mutated_offspring (list): List of mutated strategies.
    """
    for i in range(len(offspring)):
        if random.random() < mutation_rate:
            mutation_point = random.randint(0, 2)
            offspring[i][mutation_point] = 1 - offspring[i][mutation_point]

    return offspring

def evolve(size, num_generations, give_up_after, num_iterations, selection_proportion, crossover_rate, mutation_rate):
    """
    Evolves strategies over a number of generations for the Iterated Prisoner's Dilemma.

    Args:
        size (int): Initial population size
        num_generations (int): Number of generations
        give_up_after (int): Number of generations to give up after if best solution has remained unchanged
        selection_proportion (float): The proportion of the population to be selected (survive) on each generation
        crossover_rate (float): Probability of a selected pair of solutions to sexually reproduce
        mutation_rate (float): Probability of a selected offspring to undergo mutation

    Returns:
        results (str): The results of the evolution in TSV format
    """

    population = initialise_population(size)
    fitnesses = list_fitnesses(population, num_iterations)
    current_best = get_best(population, fitnesses, 0)

    results = ["Generation\tBestFitness\tBestStrategy\tAvgFitness\t000\t001\t010\t011\t100\t101\t110\t111"]
    results.append(f"0\t{current_best['fitness']}\t{"".join(map(str, current_best['strategy']))}\t{sum(fitnesses) / len(fitnesses)}\t{population.count([0,0,0])}\t{population.count([0,0,1])}\t{population.count([0,1,0])}\t{population.count([0,1,1])}\t{population.count([1,0,0])}\t{population.count([1,0,1])}\t{population.count([1,1,0])}\t{population.count([1,1,1])}")

    for generation in range(1, num_generations):
        population = tournament_selection(population, fitnesses, int(len(population) *selection_proportion))
        offspring = crossover(population, crossover_rate, size - len(population))
        population += mutate(offspring, mutation_rate)

        fitnesses = list_fitnesses(population, num_iterations)
        generation_best = get_best(population, fitnesses, generation)

        if (generation_best['fitness'] > current_best['fitness']):
            current_best = generation_best
            print(f"New best strategy: {current_best['strategy']}, {current_best['fitness']}")

        results.append(f"{generation}\t{current_best['fitness']}\t{"".join(map(str, current_best['strategy']))}\t{sum(fitnesses) / len(fitnesses)}\t{population.count([0,0,0])}\t{population.count([0,0,1])}\t{population.count([0,1,0])}\t{population.count([0,1,1])}\t{population.count([1,0,0])}\t{population.count([1,0,1])}\t{population.count([1,1,0])}\t{population.count([1,1,1])}")

        if (generation - current_best['generation'] >= give_up_after):
            break

    print(f"Best strategy: {current_best['strategy']}")
    print(f"Fitness: {current_best['fitness']}")
    print(f"Generation: {current_best['generation']}")

    return results

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Program to evolve strategies for the Iterated Prisoner's Dilemma")
    parser.add_argument("-s", "--size", type=int, help="Initial population size", required=False, default=75)
    parser.add_argument("-g", "--num-generations", type=int, help="Number of generations", required=False, default=500)
    parser.add_argument("-a", "--give-up-after", type=int, help="Number of generations to give up after if best solution has remained unchanged", required=False, default=50)
    parser.add_argument("-i", "--num-iterations", type=int, help="Number of iterations of the dilemma between two agents", required=False, default=10)
    parser.add_argument("-p", "--selection-proportion", type=float, help="The proportion of the population to be selected (survive) on each generation", required=False, default=0.2)
    parser.add_argument("-c", "--crossover-rate", type=float, help="Probability of a selected pair of solutions to sexually reproduce", required=False, default=0.8)
    parser.add_argument("-m", "--mutation-rate", type=float, help="Probability of a selected offspring to undergo mutation", required=False, default=0.1)
    parser.add_argument("-o", "--output-file", type=str, help="File to write TSV results to", required=False, default="output.tsv")
    args=parser.parse_args()

    results = evolve(args.size, args.num_generations, args.give_up_after, args.num_iterations, args.selection_proportion, args.crossover_rate, args.mutation_rate)

    if (args.output_file):
        with open(args.output_file, "w") as f:
            for result in results:
                f.write(result + "\n")
