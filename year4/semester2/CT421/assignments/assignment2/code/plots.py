#!/usr/bin/python3

import pandas as pd
import matplotlib.pyplot as plt
import argparse

# Set up argument parser
parser = argparse.ArgumentParser(description='Plot fitness and strategy counts from a TSV file.')
parser.add_argument('file_path', type=str, help='Path to the TSV file')
args = parser.parse_args()

# Read the file
df = pd.read_csv(args.file_path, sep="\t")

df['Generation'] = pd.to_numeric(df['Generation'], errors='coerce')

# Plot 1: Best Fitness & Average Fitness over Generations
plt.figure(figsize=(10, 5))
plt.plot(df['Generation'], df['BestFitness'], label='Best Fitness', marker='o')
plt.plot(df['Generation'], df['AvgFitness'], label='Average Fitness', linestyle='dashed', marker='s')
plt.xlabel('Generation')
plt.ylabel('Fitness')
plt.title('Best & Average Fitness Over Generations')
plt.legend()
plt.grid(True)
plt.show()

# Plot 2: Strategy Counts over Generations
plt.figure(figsize=(10, 5))
strategies = ["000", "001", "010", "011", "100", "101", "110", "111"]

for strategy in strategies:
    if strategy in df.columns:
        plt.plot(df['Generation'], df[strategy], label=strategy, marker='.')

plt.xlabel('Generation')
plt.ylabel('Strategy Count')
plt.title('Strategy Counts Over Generations')
plt.legend(title="Strategies")
plt.grid(True)
plt.show()
