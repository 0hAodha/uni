#!/usr/bin/python3

import argparse
import random
import math
import time

# function to read a TSP file in and construct a dictionary of its attributes
def graph_from_file(input_file):
    graph = {}

    print("Reading cities data from: " + input_file)

    with open(input_file, "r") as file: lines=file.readlines()

    # assuming that the data will be available on specific line numbers and that fields must be included, which works for the provided datasets.
    # i would implement this in a better way if python didn't have such a terrible regex engine compared to perl (further evidence of why perl is the best and python sucks)
    graph["name"] = lines[0].split()[1]
    graph["type"] = lines[1].split()[1]
    graph["comment"] = lines[2][lines[2].find(" ") + 1:]
    graph["dimension"] = lines[3].split()[1]
    graph["edge_weight_type"] = lines[4].split()[1]

    cities = []
    for line_number in range(6, len(lines)):
        if lines[line_number] == "EOF\n":
            break

        fields = lines[line_number].split()
        cities.append({"name": int(fields[0]), "x": float(fields[1]), "y": float(fields[2]) })

    graph["cities"] = cities

    return graph

# function to generate an adjacency matrix from a graph
# assumes each cities name is an integer starting at 1. city 1 is indexed at row,column 0, city 2 at row,column 1, ..., city n at row,column n-1
def adjacency_matrix_from_graph(graph):
    matrix = []
    cities = sorted(graph["cities"], key=lambda city: city["name"]) # sorting list of cities in case not sorted, assuming each city's name is an int

    # iterating over sorted list of cities
    for city in cities:
        distances = []

        for other_city in cities:
            distances.append(dist(city, other_city))

        matrix.append(distances)

    return matrix

# function to calculate the euclidean distance between two cities
def dist(city1, city2):
    return math.sqrt( (city2["x"] - city1["x"] )**2 + ( city2["y"] - city1["y"] )**2 )


# function to initialise a population of potential solutions
# each potential solution is a list of city names, representing a permutation of the set of city names
# the start city is random, and it is implied that the salesman must return from the last city in the list to the start city, i.e.  the start city appears only once in the list
def initialise_population(size, graph):
    city_names = [city["name"] for city in graph["cities"]]
    tours = []

    for i in range(size):
        tours.append(random.sample(city_names, len(city_names)))

    return tours


# function to calculate the fitness (total length) of a given tour
def fitness(tour, adjacency_matrix):
    total_length = 0

    for i in range(len(tour)):
        # if city in question is the last city in the tour, get length to start city
        if (i+1 == len(tour)):
            total_length += adjacency_matrix[ tour[i] - 1 ][ tour[0] - 1 ]  # recall that the tour contains the name of the city using 1-based indexing, but the adjacency matrix uses 0-based indexing, so subtract 1 from the tour's name for a city
        else:
            total_length += adjacency_matrix[ tour[i] - 1 ][ tour[i+1] - 1 ]

    return total_length


# function to calculate the fitness for each element in a list
def list_of_fitnesses(tours, adjacency_matrix):
    return [fitness(tour, adjacency_matrix) for tour in tours]


# function to return a dictionary containing the details of the current best solution
def get_current_best(population, fitnesses, generation):
    # not using min() and then indexof to find the best solution because that would be less efficient
    index_of_current_best = min(enumerate(fitnesses), key=lambda index_fitness_tuple: index_fitness_tuple[1])[0]

    # creating a dictionary to store the details of the current best solution
    current_best = {
        "tour": population[index_of_current_best],
        "fitness": fitnesses[index_of_current_best],
        "generation": generation
    }
    return current_best


# function to randomly remove individuals for the population, with good solutions less likely to be removed and bad solutions more likely
# the number to be removed is defined by how many offspring were produced in this generation, to keep population stable
def deselect(population, fitnesses, num_to_remove):
    # Compute survival probabilities in a single pass
    total_inverse_fitness = sum(1.0 / fitness for fitness in fitnesses)
    survival_probabilities = [(1.0 / fitness) / total_inverse_fitness for fitness in fitnesses]

    # Select individuals to remove using weighted random sampling
    indices_to_remove = set(random.choices( range(len(population)), weights=[1 - p for p in survival_probabilities], k=num_to_remove))

    survivors = [solution for index, solution in enumerate(population) if index not in indices_to_remove]

    return survivors

# general crossover function
def crossover(population, crossover_rate, number_to_replace):
    offspring = []

    # iterate until the desired number of offspring are produced (fraction of the current population)
    while len(offspring) < number_to_replace:
        # randomly select two parents
        parent1, parent2 = random.sample(population, 2)

        # probabilistically determine whether the pair will reproduce based off the crossover rate
        if random.random() < crossover_rate:
            # randomly alternate between the two crossover operators (50-50)
            if random.random() < 0.5:
                child = pmx_crossover(parent1, parent2)
            else:
                child = ox_crossover(parent1, parent2)

            offspring.append(child)

    return offspring

# function to perform partially mapped crossover on two parents
def pmx_crossover(parent1, parent2):
    size = len(parent1)
    child = [None] * size

    # choose random crossover points
    crossover_point1 = random.randint(0, size // 2)
    crossover_point2 = random.randint(crossover_point1, size)

    # copy the middle segment defined by the crossover points from parent1 to the child
    for index in range(crossover_point1, crossover_point2):
        child[index] = parent1[index]

    # fill in the remaining positions using parent2 and the pmx mapping
    for index in range(size):
        if child[index] is None:
            gene = parent2[index]
            while gene in child:
                gene = parent2[child.index(None)]
            child[index] = gene

    return child

# function to perform order crossover on two parents
def ox_crossover(parent1, parent2):
    size = len(parent1)
    child = [None] * size

    # select a two random indexes from parent1 to create a segment for crossover
    crossover_point1 = random.randint(0, size // 2)
    crossover_point2 = random.randint(crossover_point1, size)

    # copy the segment from parent1 to the offspring
    for index in range(crossover_point1, crossover_point2):
        child[index] = parent1[index]

    # fill remaining positions with parent2, avoiding duplicates
    current_position = crossover_point2
    for index in range(size):
        if parent2[index] not in child:
            if current_position == size:
                current_position = 0

            child[current_position] = parent2[index]
            current_position += 1

    return child


# general mutation function
def mutate(offspring, mutation_rate):
    offspring = []
    return offspring


def main():
    parser = argparse.ArgumentParser(description="Program to solve the travelling salesman problem for a given TSP file using a genetic algorithm.")
    parser.add_argument("-i", "--input-file", type=str, help="Path to input file in TSP format", required=True)
    parser.add_argument("-s", "--size", type=int, help="Initial population size", required=False, default=100)
    parser.add_argument("-g", "--num-generations", type=int, help="Number of generations", required=False, default=500)
    parser.add_argument("-a", "--give-up-after", type=int, help="Number of generations to give up after if best solution has remained unchanged", required=False, default=100)
    parser.add_argument("-d", "--deselection-proportion", type=float, help="The proportion of the population to be deselected on each generation", required=False, default=0.7)
    parser.add_argument("-c", "--crossover-rate", type=float, help="Probability of a selected pair of solutions to sexually reproduce", required=False, default=0.8)
    parser.add_argument("-m", "--mutation-rate", type=float, help="Probability of a selected offspring to undergo mutation", required=False, default=0.2)
    args=parser.parse_args()

    print("Input file: " + str(args.input_file))
    print("Initial population size: " + str(args.size))
    print("Number of generations: " + str(args.num_generations))
    print("Will give up after: " + str(args.give_up_after) + " generations")
    print("Deselection proportion: " + str(args.deselection_proportion))
    print("Crossover rate: " + str(args.crossover_rate))
    print("Mutation rate: " + str(args.crossover_rate))

    graph = graph_from_file(args.input_file)
    adjacency_matrix = adjacency_matrix_from_graph(graph)

    # get initial population & its details
    population = initialise_population(args.size, graph)
    fitnesses = list_of_fitnesses(population, adjacency_matrix)
    current_best = get_current_best(population, fitnesses, 0)

    # appending results to an array of strings rather than to a string as it's more efficient
    results = ["timestamp,generation,population_size,avg_fitness,generation_best,current_best"]
    results.append(str(time.time()) + "," + "0," + str(len(population)) + "," + str(sum(fitnesses)/len(fitnesses)) + "," + str(current_best["fitness"]) + "," + str(current_best["fitness"]))

    number_to_replace = int(len(population) * args.deselection_proportion)

    # this is where efficiency gets critical lol
    for generation in range(args.num_generations):
        print("Generation " + str(generation) + " of " + str(args.num_generations))
        # deselect solutions from population probabilistically
        population = deselect(population, fitnesses, number_to_replace)

        # create a number of offspring with crossover to replace the number deselected
        offspring = crossover(population, args.crossover_rate, number_to_replace)

        # mutate offspring and add them to the original population to restore size
        # population.append(mutate(offspring, args.mutation_rate))
        population += offspring

        # calculate fitnesses
        fitnesses = list_of_fitnesses(population, adjacency_matrix)

        # getting best solution from this generation and updating current_best if relevant
        generation_best = get_current_best(population, fitnesses, generation)
        if generation_best["fitness"] < current_best["fitness"]:
            current_best = generation_best

        results.append(str(time.time()) + "," + str(generation) + "," + str(len(population)) + "," + str(sum(fitnesses)/len(fitnesses)) + "," + str(generation_best["fitness"]) + "," + str(current_best["fitness"]))

        if (generation - current_best["generation"]) >= args.give_up_after:
            print("Best solution has not changed in " + str(args.give_up_after) + " generations. Giving up.")
            break

    print("Best solution found: " + str(current_best["tour"]))
    print("Fitness of best solution: " + str(current_best["fitness"]))
    print("Best solution found in generation: " + str(current_best["generation"]))

    with open("output.csv", "w") as file:
        for line in results:
            file.write(line + "\n")


if __name__ == "__main__":
    main()
