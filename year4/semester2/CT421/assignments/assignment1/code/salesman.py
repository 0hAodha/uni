#!/usr/bin/python3

import argparse
import re
import math

# function to read a TSP file in and construct a dictionary of its attributes
def graph_from_file(input_file):
    graph = {}

    print("Reading cities data from: " + input_file)

    with open(input_file, "r") as file:
        lines=file.readlines()

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


def main():
    parser = argparse.ArgumentParser(description="Program to solve the travelling salesman problem for a given TSP file using a genetic algorithm.")
    parser.add_argument("-i", "--input-file", type=str, help="Path to input file in TSP format", required=True)
    args=parser.parse_args()

    input_file=args.input_file

    graph = graph_from_file(input_file)
    adjacency_matrix = adjacency_matrix_from_graph(graph)

    for row in adjacency_matrix:
        print(row)


if __name__ == "__main__":
    main()
