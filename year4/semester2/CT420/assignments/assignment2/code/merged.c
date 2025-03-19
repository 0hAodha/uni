// Compile code with gcc -o bm1 bm1.c -lrt -Wall -O2
// Execute code with sudo ./bm1

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <signal.h>
#include <sys/mman.h>
#include <unistd.h>
#include <sched.h>
#include <errno.h>
#include <string.h>
#include <limits.h>

#define ITERATIONS 10000
#define NS_PER_SEC 1000000000L

timer_t timer_id;
volatile sig_atomic_t timer_expired = 0;
volatile sig_atomic_t signal_received = 0;
struct timespec start, end, sleep_time;

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

void signal_handler(int signum) {
    signal_received = 1;
	clock_gettime(CLOCK_MONOTONIC, &end);
}

void timer_handler(int signum) {
    timer_expired = 1;
    clock_gettime(CLOCK_MONOTONIC, &end);
}

void configure_realtime_scheduling() {
    struct sched_param param;
    param.sched_priority = sched_get_priority_max(SCHED_FIFO);
    if (sched_setscheduler(0, SCHED_FIFO, &param) == -1) {
        perror("sched_setscheduler");
        exit(EXIT_FAILURE);
    }
}

void lock_memory() {
    if (mlockall(MCL_CURRENT | MCL_FUTURE) == -1) {
        perror("mlockall");
        exit(EXIT_FAILURE);
    }
}

void benchmark_nanosleep() {
    long long jitter_data[ITERATIONS];
    sleep_time.tv_sec = 0;
    sleep_time.tv_nsec = 1000000; // 1 ms

    for (int i = 0; i < ITERATIONS; i++) {
        clock_gettime(CLOCK_MONOTONIC, &start);
        nanosleep(&sleep_time, NULL);
        clock_gettime(CLOCK_MONOTONIC, &end);

        jitter_data[i] = ((end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec)) - sleep_time.tv_nsec;
    }
    save_results("nanosleep.csv", jitter_data);
}

void benchmark_signal_latency() {
    long long latency_data[ITERATIONS];
    signal(SIGUSR1, signal_handler);

    for (int i = 0; i < ITERATIONS; i++) {
        clock_gettime(CLOCK_MONOTONIC, &start);
        kill(getpid(), SIGUSR1);
        while (!signal_received);

        latency_data[i] = (end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec);
        signal_received = 0;
    }
    save_results("signal_latency.csv", latency_data);
}

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
        while (!timer_expired);

        clock_gettime(CLOCK_MONOTONIC, &end);
        jitter_data[i] = ((end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec)) - its.it_interval.tv_nsec;
        timer_expired = 0;
        start = end;
    }
    save_results("timer.csv", jitter_data);
}

void benchmark_usleep() {
    long long jitter_data[ITERATIONS];

    for (int i = 0; i < ITERATIONS; i++) {
        clock_gettime(CLOCK_MONOTONIC, &start);
        usleep(1000); // 1 ms
        clock_gettime(CLOCK_MONOTONIC, &end);

        jitter_data[i] = ((end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec)) - 1000000;
    }
    save_results("usleep.csv", jitter_data);
}

int main() {
    configure_realtime_scheduling();
    lock_memory();

    benchmark_nanosleep();
    benchmark_signal_latency();
    benchmark_timer();
    benchmark_usleep();

    return 0;
}
