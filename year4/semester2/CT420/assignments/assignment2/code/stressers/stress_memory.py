import argparse
import time
import psutil

def stress_memory(target_usage: float):
    """
    Stress the system memory to a given percentage.
    
    :param target_usage: Target memory usage (0.0 to 1.0, where 1.0 is 100%)
    """
    total_memory = psutil.virtual_memory().total  # Get total RAM in bytes
    target_memory = int(total_memory * target_usage)  # Calculate target memory size

    print(f"Total Memory: {total_memory / (1024**3):.2f} GB")
    print(f"Target Memory Usage: {target_memory / (1024**3):.2f} GB ({target_usage * 100:.0f}%)")

    try:
        memory_hog = []  # List to store allocated memory chunks
        chunk_size = 100 * 1024 * 1024  # Allocate in 100MB chunks

        while sum(len(chunk) for chunk in memory_hog) < target_memory:
            memory_hog.append(bytearray(chunk_size))  # Allocate memory
            time.sleep(0.1)  # Small delay to allow system response

        print("Memory fully allocated. Holding...")
        while True:  # Keep the memory occupied
            time.sleep(1)

    except MemoryError:
        print("Memory limit reached. Exiting...")
    except KeyboardInterrupt:
        print("Memory stress test stopped.")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Memory Stress Test Script")
    parser.add_argument("--usage", type=float, default=1.0, help="Target memory usage (default: 1.0 for 100%)")
    args = parser.parse_args()

    stress_memory(args.usage)
