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


# function to perform monte carlo (roulette wheel) selection on a population
def select(population, fitnesses, number_to_select):
    total_fitness = sum(fitnesses)
    weights = [ 1 - (fitness / total_fitness) for fitness in fitnesses] # subtract the relative fitness of each solution from one so that bigger number = worse fitness = more likely to die

    return random.choices(population, weights, k=number_to_select)


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

# function to perform partially mapped crossover (as defined on wikipedia) on two parents
def pmx_crossover(parent1, parent2):
    size = len(parent1)
    child = [None] * size

    # generate random crossover points between 0 and the size of the parent, inclusive
    crossover_point1 = random.randint(0, size)
    crossover_point2 = random.randint(0, size)

    # swap crossover points if second is before first
    if crossover_point2 < crossover_point1:
        crossover_point1, crossover_point2 = crossover_point2, crossover_point1

    # copy selected section to child chromosome in same position
    child[crossover_point1:crossover_point2] = parent1[crossover_point1:crossover_point2]

    # unmapped_indices = list(range(crossover_point2, size)) + list(range(0, crossover_point1))
    # unmapped_indices = [index for index, value in enumerate(child) if value == None]

    # look for genes that have not been copied in the corresponding segment of parent2 starting at the first crossover point
    # for each gene found m, look up in the child which element n was copied in its place from parent1. copy m to the position held by n in parent2 if not occupied, else continue
    for index in range(crossover_point1, crossover_point2): 
        m = parent2[index]
        if m not in child:
            n = child[index]
            index_n = parent2.index(n)
            if child[index_n] == None:
                child[index_n] = m
            # if the place taken by n in parent2 is already occupied by an element k in the child, m is put in the place taken by k in parent2
            else: 
                k = child[index_n]
                child[parent2.index(k)] = m

    # after processing the genes from the selected segment in parent2, the remaining positions in the child are filled with the genes from parent2 that have not yet been copied in the order of their appearance
    child_index = 0
    for parent_index in range(size):
        if parent2[parent_index] not in child:
            while child[child_index] != None:
                child_index += 1
            child[child_index] = parent2[parent_index]

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
    parser.add_argument("-p", "--selection-proportion", type=float, help="The proportion of the population to be selected (survive) on each generation", required=False, default=0.2)
    parser.add_argument("-c", "--crossover-rate", type=float, help="Probability of a selected pair of solutions to sexually reproduce", required=False, default=0.8)
    parser.add_argument("-m", "--mutation-rate", type=float, help="Probability of a selected offspring to undergo mutation", required=False, default=0.2)
    args=parser.parse_args()

    print("Input file: " + str(args.input_file))
    print("Initial population size: " + str(args.size))
    print("Number of generations: " + str(args.num_generations))
    print("Will give up after: " + str(args.give_up_after) + " generations")
    print("Selection proportion: " + str(args.selection_proportion))
    print("Crossover rate: " + str(args.crossover_rate))
    print("Mutation rate: " + str(args.crossover_rate))

    graph = graph_from_file(args.input_file)
    adjacency_matrix = adjacency_matrix_from_graph(graph)

    # get initial population & its details
    population = initialise_population(args.size, graph)
    fitnesses = list_of_fitnesses(population, adjacency_matrix)
    current_best = get_current_best(population, fitnesses, 0)

    # appending results to an array of strings rather than to a string as it's more efficient
    results = ["timestamp\tgeneration\tpopulation_size\tavg_fitness\tgeneration_best\tcurrent_best"]
    results.append(str(time.time()) + "\t" + "0\t" + str(len(population)) + "\t" + str(sum(fitnesses)/len(fitnesses)) + "\t" + str(current_best["fitness"]) + "\t" + str(current_best["fitness"]))

    # this is where efficiency gets critical lol
    for generation in range(1, args.num_generations):
        print("Generation " + str(generation) + " of " + str(args.num_generations))
        # deselect solutions from population probabilistically
        population = select(population, fitnesses, int(len(population) * args.selection_proportion))

        # create a number of offspring with crossover to replace the number deselected
        offspring = crossover(population, args.crossover_rate, args.size - len(population))

        # mutate offspring and add them to the original population to restore size
        population += offspring

        # calculate fitnesses
        fitnesses = list_of_fitnesses(population, adjacency_matrix)

        # getting best solution from this generation and updating current_best if relevant
        generation_best = get_current_best(population, fitnesses, generation)
        if generation_best["fitness"] < current_best["fitness"]:
            current_best = generation_best

        results.append(str(time.time()) + "\t" + str(generation) + "\t" + str(len(population)) + "\t" + str(sum(fitnesses)/len(fitnesses)) + "\t" + str(generation_best["fitness"]) + "\t" + str(current_best["fitness"]))

        if (generation - current_best["generation"]) >= args.give_up_after:
            print("Best solution has not changed in " + str(args.give_up_after) + " generations. Giving up.")
            break

    print("Best solution found: " + str(current_best["tour"]))
    print("Fitness of best solution: " + str(current_best["fitness"]))
    print("Best solution found in generation: " + str(current_best["generation"]))

    with open("output.tsv", "w") as file:
        for line in results:
            file.write(line + "\n")


if __name__ == "__main__":
    main()
