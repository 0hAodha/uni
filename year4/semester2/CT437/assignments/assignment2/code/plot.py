import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

plt.style.use('seaborn-v0_8-muted')

file_path = "results.tsv"  # Update this path if needed
df = pd.read_csv(file_path, sep='\t')

color_encrypt = "#AEC6CF"  # Pastel blue
color_decrypt = "#F4C2C2"  # Pastel red

# Filter data for 100MB and 1000MB
df_100MB = df[df["Data Size (MB)"] == 100]
df_1000MB = df[df["Data Size (MB)"] == 1000]

# Function to create and save bar chart
def create_bar_chart(df, title, filename):
    labels = df.apply(lambda row: f"{row['Cipher']}, {row['Key Size']} bits, {row['Mode']}", axis=1)
    x = np.arange(len(labels))  # Label locations
    width = 0.35  # Bar width

    fig, ax = plt.subplots(figsize=(12, 6))
    ax.bar(x - width/2, df["Encryption Time (s)"], width, label="Encryption Time", color=color_encrypt)
    ax.bar(x + width/2, df["Decryption Time (s)"], width, label="Decryption Time", color=color_decrypt)

    # Labels and title
    ax.set_xlabel("Cipher, Keysize (bits), Mode")
    ax.set_ylabel("Time (s)")
    ax.set_title(title)
    ax.set_xticks(x)
    ax.set_xticklabels(labels, rotation=45, ha="right")
    ax.legend()

    ax.grid(True, axis='y', linestyle='--', alpha=0.5)

    plt.tight_layout()
    plt.savefig(filename, dpi=300)
    plt.close()

# Create and save bar charts
create_bar_chart(df_100MB, "Encryption & Decryption Times for 100MB Data", "../latex/images/100mb.png")
create_bar_chart(df_1000MB, "Encryption & Decryption Times for 1000MB Data", "../latex/images/1000mb.png")
