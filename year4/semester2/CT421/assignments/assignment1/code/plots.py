#!/usr/bin/python3

import matplotlib.pyplot as plt
import pandas as pd
import argparse

# Load TSV file
def plot_fitness_trends(file_path):
    df = pd.read_csv(file_path, sep='\t')
    
    plt.figure(figsize=(10, 5))
    plt.plot(df['generation'], df['avg_fitness'], label='Avg Fitness', marker=None)
    plt.plot(df['generation'], df['generation_best'], label='Generation Best', marker=None)
    plt.plot(df['generation'], df['current_best'], label='Current Best', marker=None)
    
    plt.xlabel('Generation')
    plt.ylabel('Fitness')
    plt.title('Fitness Trends Across Generations')
    plt.legend()
    plt.grid()
    plt.show()


# Main function
def main():
    parser = argparse.ArgumentParser(description='Plot fitness trends from a TSV file.')
    parser.add_argument('file_path', type=str, help='Path to the TSV file')
    args = parser.parse_args()
    
    plot_fitness_trends(args.file_path)

if __name__ == "__main__":
    main()
