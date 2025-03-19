import multiprocessing
import time
import argparse
import os

def stress_cpu(workload: float):
    """
    Function to create CPU load. Uses a busy-wait method to simulate CPU usage.
    
    :param workload: The fraction of time (0.0 to 1.0) the CPU should be busy.
    """
    cycle_time = 0.1  # Total cycle time (100ms per iteration)
    busy_time = cycle_time * workload  # Time to stay busy
    idle_time = cycle_time - busy_time  # Time to stay idle

    while True:
        start_time = time.time()
        while (time.time() - start_time) < busy_time:
            pass  # Busy wait
        time.sleep(idle_time)  # Sleep to control CPU usage

def start_stress_test(load: str):
    """
    Starts CPU stress test based on load level.
    
    :param load: 'medium' (~50% load) or 'high' (~100% load)
    """
    num_cores = os.cpu_count() or 4  # Use all available CPU cores
    workload = 0.5 if load == "medium" else 1.0  # Set workload percentage

    print(f"Starting {load.upper()} CPU stress test on {num_cores} cores...")

    processes = []
    for _ in range(num_cores):
        p = multiprocessing.Process(target=stress_cpu, args=(workload,))
        p.start()
        processes.append(p)

    try:
        for p in processes:
            p.join()
    except KeyboardInterrupt:
        print("Stopping stress test...")
        for p in processes:
            p.terminate()
            p.join()

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="CPU Stress Test Script")
    parser.add_argument("--load", choices=["medium", "high"], required=True, help="Choose CPU load level (medium or high)")
    args = parser.parse_args()

    start_stress_test(args.load)
