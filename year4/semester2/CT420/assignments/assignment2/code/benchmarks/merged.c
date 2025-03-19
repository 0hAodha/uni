// Compile code with gcc -o merged merged.c -lrt -Wall -O2
// Execute code with sudo ./merged

#include <stdio.h>      // Standard I/O functions
#include <stdlib.h>     // Standard library functions
#include <time.h>       // Time-related functions
#include <signal.h>     // Signal handling
#include <sys/mman.h>   // Memory locking
#include <unistd.h>     // POSIX standard functions
#include <sched.h>      // Scheduling policies
#include <errno.h>      // Error handling
#include <string.h>     // String manipulation
#include <limits.h>     // Limits of integral types

// Constants
#define ITERATIONS 10000    // Number of benchmark iterations
#define NS_PER_SEC 1000000000L // Nanoseconds per second

// Global Variables
timer_t timer_id;  // Timer identifier
volatile sig_atomic_t timer_expired = 0;  // Flag for timer expiration
volatile sig_atomic_t signal_received = 0; // Flag for signal reception
struct timespec start, end, sleep_time; // Time structures for benchmarking

// Function to save benchmark results to a CSV file
void save_results(const char *filename, long long *data) {
    FILE *file = fopen(filename, "w");
    if (!file) {
        perror("fopen");
        exit(EXIT_FAILURE);
    }
    fprintf(file, "Iteration,Latency/Jitter (ns)\n");
    for (int i = 0; i < ITERATIONS; i++) {
        fprintf(file, "%d,%lld\n", i, data[i]);
    }
    fclose(file);
}

// Signal handler for signal-based latency measurement
void signal_handler(int signum) {
    signal_received = 1; // Mark signal as received
    clock_gettime(CLOCK_MONOTONIC, &end); // Capture end time
}

// Timer signal handler
void timer_handler(int signum) {
    timer_expired = 1; // Mark timer as expired
    clock_gettime(CLOCK_MONOTONIC, &end); // Capture end time
}

// Configures real-time scheduling with FIFO priority
void configure_realtime_scheduling() {
    struct sched_param param;
    param.sched_priority = sched_get_priority_max(SCHED_FIFO);
    if (sched_setscheduler(0, SCHED_FIFO, &param) == -1) {
        perror("sched_setscheduler");
        exit(EXIT_FAILURE);
    }
}

// Locks memory to prevent paging for real-time performance
void lock_memory() {
    if (mlockall(MCL_CURRENT | MCL_FUTURE) == -1) {
        perror("mlockall");
        exit(EXIT_FAILURE);
    }
}

// Measures jitter of nanosleep function
void benchmark_nanosleep() {
    long long jitter_data[ITERATIONS];
    sleep_time.tv_sec = 0;
    sleep_time.tv_nsec = 1000000; // 1 ms sleep

    for (int i = 0; i < ITERATIONS; i++) {
        clock_gettime(CLOCK_MONOTONIC, &start);
        nanosleep(&sleep_time, NULL);
        clock_gettime(CLOCK_MONOTONIC, &end);

        jitter_data[i] = ((end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec)) - sleep_time.tv_nsec;
    }
    save_results("nanosleep.csv", jitter_data);
}

// Measures latency of sending and handling a signal
void benchmark_signal_latency() {
    long long latency_data[ITERATIONS];
    signal(SIGUSR1, signal_handler); // Register signal handler

    for (int i = 0; i < ITERATIONS; i++) {
        clock_gettime(CLOCK_MONOTONIC, &start);
        kill(getpid(), SIGUSR1); // Send signal to itself
        while (!signal_received); // Wait for signal to be handled

        latency_data[i] = (end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec);
        signal_received = 0;
    }
    save_results("signal_latency.csv", latency_data);
}

// Measures jitter of a real-time timer
void benchmark_timer() {
    long long jitter_data[ITERATIONS];
    struct sigevent sev;
    sev.sigev_notify = SIGEV_SIGNAL;
    sev.sigev_signo = SIGRTMIN;
    sev.sigev_value.sival_ptr = &timer_id;

    if (timer_create(CLOCK_MONOTONIC, &sev, &timer_id) == -1) {
        perror("timer_create");
        exit(EXIT_FAILURE);
    }

    struct itimerspec its;
    its.it_value.tv_sec = 0;
    its.it_value.tv_nsec = 1000000; // 1 ms
    its.it_interval = its.it_value;
    signal(SIGRTMIN, timer_handler);

    if (timer_settime(timer_id, 0, &its, NULL) == -1) {
        perror("timer_settime");
        exit(EXIT_FAILURE);
    }
    clock_gettime(CLOCK_MONOTONIC, &start);
    for (int i = 0; i < ITERATIONS; i++) {
        while (!timer_expired) {
            struct timespec ts = {0, 100};
            nanosleep(&ts, NULL);
        }

        clock_gettime(CLOCK_MONOTONIC, &end);
        jitter_data[i] = ((end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec)) - its.it_interval.tv_nsec;
        timer_expired = 0;
        start = end;
    }
    save_results("timer.csv", jitter_data);
}

// Measures jitter of usleep function
void benchmark_usleep() {
    long long jitter_data[ITERATIONS];

    for (int i = 0; i < ITERATIONS; i++) {
        clock_gettime(CLOCK_MONOTONIC, &start);
        usleep(1000); // Sleep for 1 ms
        clock_gettime(CLOCK_MONOTONIC, &end);

        jitter_data[i] = ((end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec)) - 1000000;
    }
    save_results("usleep.csv", jitter_data);
}

// Main function to execute all benchmarks
int main() {
    configure_realtime_scheduling(); // Set high priority scheduling
    lock_memory(); // Prevent memory paging

    printf("Getting nanosleep benchmark\n");
    benchmark_nanosleep();

    printf("Getting signal benchmark\n");
    benchmark_signal_latency();

    printf("Getting timer benchmark\n");
    benchmark_timer();

    printf("Getting usleep benchmark\n");
    benchmark_usleep();

    return 0;
}
