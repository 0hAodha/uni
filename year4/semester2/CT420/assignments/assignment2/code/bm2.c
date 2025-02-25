// Compile code with gcc -o bm2 bm2.c -lrt -Wall -O2
// Execute code with sudo ./bm2

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

#define ITERATIONS 1000
#define NS_PER_SEC 1000000000L

timer_t timer_id;
volatile sig_atomic_t timer_expired = 0;
struct timespec start, end;

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

void benchmark_timer() {
    long long total_jitter = 0;
    long long max_jitter = 0;
    long long min_jitter = LLONG_MAX;

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

        long long elapsed = (end.tv_sec - start.tv_sec) * NS_PER_SEC + (end.tv_nsec - start.tv_nsec);
        long long jitter = elapsed - its.it_interval.tv_nsec;

        total_jitter += llabs(jitter);
        if (jitter > max_jitter) max_jitter = jitter;
        if (jitter < min_jitter) min_jitter = jitter;

        timer_expired = 0;
        start = end;
    }

    printf("\nTimer Benchmark:\n");
    printf("Average jitter: %lld ns\n", total_jitter / ITERATIONS);
    printf("Max jitter: %lld ns\n", max_jitter);
    printf("Min jitter: %lld ns\n", min_jitter);

    timer_delete(timer_id);
}

int main() {
    configure_realtime_scheduling();
    lock_memory();

    benchmark_timer();

    return 0;
}
