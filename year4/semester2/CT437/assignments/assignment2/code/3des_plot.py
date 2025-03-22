import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

plt.style.use('seaborn-v0_8-muted')

# Load data
file_path = "3des_results.tsv"  # Update if using .csv or another name
df = pd.read_csv(file_path, sep='\t')

# Add a new label for combined grouping
df["Label"] = df.apply(lambda row: f"{row['Cipher']}, {row['Key Size']}b, {row['Mode']}, {row['Data Size (MB)']}MB", axis=1)

# Sort to keep logical order
df = df.sort_values(by=["Cipher", "Key Size", "Mode", "Data Size (MB)"])

# Prepare data for plotting
labels = df["Label"]
x = np.arange(len(labels))  # label positions
width = 0.35  # width of the bars

color_encrypt = "#AEC6CF"  # Pastel blue
color_decrypt = "#F4C2C2"  # Pastel red

# Create the bar chart
fig, ax = plt.subplots(figsize=(14, 6))
ax.bar(x - width/2, df["Encryption Time (s)"], width, label="Encryption Time", color=color_encrypt)
ax.bar(x + width/2, df["Decryption Time (s)"], width, label="Decryption Time", color=color_decrypt)

# Labels and styling
ax.set_xlabel("Cipher, Key Size, Mode, Data Size")
ax.set_ylabel("CPU Time (s)")
ax.set_title("Encryption & Decryption Times for All Ciphers (100MB & 1000MB)")
ax.set_xticks(x)
ax.set_xticklabels(labels)
ax.legend()
ax.grid(True, axis='y', linestyle='--', alpha=0.5)

# Save plot
plt.tight_layout()
plt.savefig("../latex/images/3des.png", dpi=300)
plt.close()
