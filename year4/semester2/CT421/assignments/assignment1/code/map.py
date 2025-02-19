#!/usr/bin/python3

import matplotlib.pyplot as plt
from salesman import graph_from_file

def plot_tour(graph, tour):
    cities = {city["name"]: (city["x"], city["y"]) for city in graph["cities"]}

    plt.figure(figsize=(8, 6))

    for city, (x, y) in cities.items():
        plt.scatter(x, y, color='blue')
        plt.text(x, y, str(city), fontsize=12, verticalalignment='bottom', horizontalalignment='right')

    for i in range(len(tour)):
        city1 = tour[i]
        city2 = tour[(i + 1) % len(tour)]  # Connects last city to first
        x_values = [cities[city1][0], cities[city2][0]]
        y_values = [cities[city1][1], cities[city2][1]]
        plt.plot(x_values, y_values, 'r-')

    plt.xlabel("X Coordinate")
    plt.ylabel("Y Coordinate")
    plt.title("Tour Path")
    plt.grid()
    plt.show()

# Example usage
graph = graph_from_file("../materials/berlin52.tsp")
tour = [16, 50, 44, 34, 46, 48, 24, 38, 40, 37, 5, 15, 4, 6, 25, 51, 12, 28, 27, 26, 14, 13, 52, 11, 33, 43, 45, 19, 10, 9, 8, 41, 17, 31, 3, 18, 32, 36, 39, 35, 49, 1, 22, 23, 20, 30, 42, 2, 7, 21, 29, 47]
plot_tour(graph, tour)
