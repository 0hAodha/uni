import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

df = pd.read_csv('performance_results.csv')

def save_heatmap(metric, title, filename):
    pivot = df.pivot(index='MapLines', columns='ReduceWords', values=metric)
    plt.figure(figsize=(8, 6))
    sns.heatmap(
        pivot,
        annot=True,
        fmt="d",
        cmap="RdYlGn_r",
        cbar_kws={'label': 'Time (ms)'}
    )
    plt.title(title)
    plt.ylabel("Lines per Map Thread")
    plt.xlabel("Words per Reduce Thread")
    plt.tight_layout()
    plt.savefig(filename)
    plt.close()
    print(f"Saved: {filename}")

save_heatmap('TotalTime', 'Total Time (ms)', '../latex/images/total_time_heatmap.png')
save_heatmap('MapTime', 'Map Time (ms)', '../latex/images/map_time_heatmap.png')
save_heatmap('ReduceTime', 'Reduce Time (ms)', '../latex/images/reduce_time_heatmap.png')
