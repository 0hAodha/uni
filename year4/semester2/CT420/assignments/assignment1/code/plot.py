#!/usr/bin/python3

import pandas as pd
import matplotlib.pyplot as plt
from datetime import datetime

# dictionary that maps IPs to server names for readability
servers = {
    "2a01:258:fffe:f": "Ireland",
    "213.5.132.231": "UK",
    "150.241.82.187": "Europe",
    "2603:c020:0:836": "US",
    "159.196.178.7": "Australia",
    "202.65.114.202": "Asia",
}

df = pd.read_csv("results.tsv", sep="\t")

df['time'] = df['timestamp'].apply(lambda x: datetime.utcfromtimestamp(x).strftime('%H:%M'))

plt.figure(figsize=(10, 6))

for remote in df['remote'].unique():
    remote_data = df[df['remote'] == remote]
    plt.plot(remote_data['time'], remote_data['jitter'], label=servers[remote], marker="o")

plt.title('Jitter over Time by Remote')
plt.xlabel('Time (UTC)')
plt.ylabel('Jitter')
plt.legend(title='Remote', loc='best')
plt.xticks(rotation=45)
plt.tight_layout()

plt.show()

plt.figure(figsize=(10, 6))

for remote in df['remote'].unique():
    remote_data = df[df['remote'] == remote]
    plt.plot(remote_data['time'], remote_data['delay'], label=servers[remote], marker="o")

plt.title('Delay over Time by Remote')
plt.xlabel('Time (UTC)')
plt.ylabel('Delay')
plt.legend(title='Remote', loc='best')
plt.xticks(rotation=45)
plt.tight_layout()

plt.show()


plt.figure(figsize=(10, 6))

for remote in df['remote'].unique():
    remote_data = df[df['remote'] == remote]
    plt.plot(remote_data['time'], remote_data['offset'], label=servers[remote], marker="o")

plt.title('Offset over Time by Remote')
plt.xlabel('Time (UTC)')
plt.ylabel('Offset')
plt.legend(title='Remote', loc='best')
plt.xticks(rotation=45)
plt.tight_layout()

plt.show()



df['server'] = df['remote'].map(servers)
jitter_statistics = df.groupby('server')['jitter'].agg(['max', 'min', 'mean', 'std']).reset_index()

print("Jitter stats:")
print(jitter_statistics)

delay_statistics = df.groupby('server')['delay'].agg(['max', 'min', 'mean', 'std']).reset_index()
print("Delay stats:")
print(delay_statistics)

offset_statistics = df.groupby('server')['offset'].agg(['max', 'min', 'mean', 'std']).reset_index()
print("Offset stats:")
print(offset_statistics)
