import pandas as pd
import matplotlib.pyplot as plt
import os
import argparse

parser = argparse.ArgumentParser(description="Plot specified metric from CSV files.")
parser.add_argument("metric", choices=["min", "max", "mean", "std"], help="Metric to plot (min, max, mean, std)")
args = parser.parse_args()

metric_to_plot = args.metric.lower()
valid_metrics = {"min": "Min", "max": "Max", "mean": "Mean", "std": "Std"}

csv_files = [
    ("../../data/Locking Enabled/1. Low CPU Load, No Swap/usleep.csv",       "Locking Enabled, Low CPU Load, No Swap"),
    ("../../data/Locking Enabled/2. Medium CPU Load, No Swap/usleep.csv",    "Locking Enabled, Medium CPU Load, No Swap"),
    ("../../data/Locking Enabled/3. High CPU Load, No Swap/usleep.csv",      "Locking Enabled, High CPU Load, No Swap"),
    ("../../data/Locking Enabled/4. Medium CPU Load, Swap/usleep.csv",       "Locking Enabled, Medium CPU Load, Swap"),
    ("../../data/Locking Enabled/5. High CPU Load, Swap/usleep.csv",         "Locking Enabled, High CPU Load, Swap"),
    ("../../data/Locking Disabled/1. Low CPU Load, No Swap/usleep.csv",      "Locking Disabled, Low CPU Load, No Swap"),
    ("../../data/Locking Disabled/2. Medium CPU Load, No Swap/usleep.csv",   "Locking Disabled, Medium CPU Load, No Swap"),
    ("../../data/Locking Disabled/3. High CPU Load, No Swap/usleep.csv",     "Locking Disabled, High CPU Load, No Swap"),
    ("../../data/Locking Disabled/4. Medium CPU Load, Swap/usleep.csv",      "Locking Disabled, Medium CPU Load, Swap"),
    ("../../data/Locking Disabled/5. High CPU Load, Swap/usleep.csv",        "Locking Disabled, High CPU Load, Swap")
]

column_name = "Latency/Jitter (ns)"

stats = {
    "Metric": [],
    "Label": [],
    "Value": []
}

for file, label in csv_files:
    if os.path.exists(file):
        df = pd.read_csv(file)

        if column_name not in df.columns:
            print(f"Warning: Column '{column_name}' not found in {file}. Available columns: {list(df.columns)}")
            continue

        values = df[column_name].dropna()
        if values.empty:
            print(f"Warning: Column '{column_name}' in {file} is empty after removing NaN values.")
            continue

        stats["Metric"].append(valid_metrics[metric_to_plot])
        stats["Label"].append(label)
        if metric_to_plot == "min":
            stats["Value"].append(values.min())
        elif metric_to_plot == "max":
            stats["Value"].append(values.max())
        elif metric_to_plot == "mean":
            stats["Value"].append(values.mean())
        elif metric_to_plot == "std":
            stats["Value"].append(values.std())
    else:
        print(f"Warning: File {file} not found.")

stats_df = pd.DataFrame(stats)

if stats_df.empty:
    print("Error: No valid data found. Ensure the column name is correct and files are properly formatted.")
else:
    fig, ax = plt.subplots(figsize=(16,4))
    ax.bar(stats_df["Label"], stats_df["Value"], color="black")

    ax.set_xticklabels(stats_df["Label"], rotation=45, ha="right")
    ax.set_ylabel("Jitter (ns)")
    ax.set_title(f"{valid_metrics[metric_to_plot]} usleep()")

    plt.tight_layout()
    plt.show()
