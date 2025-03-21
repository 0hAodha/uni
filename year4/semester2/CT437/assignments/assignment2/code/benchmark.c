#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/evp.h>
#include <sys/time.h>
#include <sys/resource.h>
#include "benchmark.h"

double get_cpu_time() {
    struct rusage usage;
    getrusage(RUSAGE_SELF, &usage);
    return (double) usage.ru_utime.tv_sec + (double) usage.ru_utime.tv_usec / 1e6;
}

int encrypt_decrypt(const EVP_CIPHER *cipher, unsigned char *key, unsigned char *iv, 
                     unsigned char *input, unsigned char *output, int encrypt, int data_size) {
    EVP_CIPHER_CTX *ctx = EVP_CIPHER_CTX_new();
    if (!ctx) {
        fprintf(stderr, "Failed to create cipher context\n");
        return -1;
    }

    int len, outlen;
    if (encrypt)
        EVP_EncryptInit_ex(ctx, cipher, NULL, key, iv);
    else
        EVP_DecryptInit_ex(ctx, cipher, NULL, key, iv);

    EVP_CipherUpdate(ctx, output, &len, input, data_size);
    outlen = len;

    EVP_CipherFinal_ex(ctx, output + len, &len);
    outlen += len;

    EVP_CIPHER_CTX_free(ctx);
    return outlen;
}

void benchmark_cipher(const EVP_CIPHER *cipher, char *cipher_name, int key_size, char *mode, int data_size) {
    unsigned char key[32] = {0};
    unsigned char iv[16] = {0};
    unsigned char *input = malloc(data_size);
    unsigned char *output = malloc(data_size);

    if (!input || !output) {
        fprintf(stderr, "Memory allocation failed\n");
        free(input);
        free(output);
        return;
    }
    memset(input, 'A', data_size);

    double start, end;
    
    // Encryption Benchmark
    start = get_cpu_time();
    encrypt_decrypt(cipher, key, iv, input, output, 1, data_size);
    end = get_cpu_time();
    double encrypt_time = end - start;

    // Decryption Benchmark
    start = get_cpu_time();
    encrypt_decrypt(cipher, key, iv, output, input, 0, data_size);
    end = get_cpu_time();
    double decrypt_time = end - start;

    // Write results to CSV file
    FILE *file = fopen(RESULTS_FILE, "a");
    if (!file) {
        fprintf(stderr, "Could not open file %s\n", RESULTS_FILE);
        free(input);
        free(output);
        return;
    }

    fprintf(file, "%s\t%d\t%s\t%d\t%.6f\t%.6f\n", cipher_name, key_size, mode, data_size / (1024 * 1024), encrypt_time, decrypt_time);
    fclose(file);

    free(input);
    free(output);
}

int main() {
    FILE *file = fopen(RESULTS_FILE, "w");
    if (!file) {
        fprintf(stderr, "Could not create file %s\n", RESULTS_FILE);
        return 1;
    }
    fprintf(file, "Cipher\tKey Size\tMode\tData Size (MB)\tEncryption Time (s)\tDecryption Time (s)\n");
    fclose(file);

    struct {
        const EVP_CIPHER *(*cipher_func)(void);
        char *name;
        int key_sizes[2];
        char *modes[3];
    } ciphers[] = {
        {EVP_aes_128_ecb, "AES", {128, 256}, {"ECB", "CBC", "CTR"}},
        {EVP_aria_128_ecb, "ARIA", {128, 256}, {"ECB", "CBC", "CTR"}},
        {EVP_camellia_128_ecb, "Camellia", {128, 256}, {"ECB", "CBC", "CTR"}}
    };

    int data_sizes[] = {DATA_SIZE_100MB, DATA_SIZE_1000MB};

    for (int i = 0; i < sizeof(ciphers) / sizeof(ciphers[0]); i++) {
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 3; k++) {
                for (int d = 0; d < 2; d++) {
                    const EVP_CIPHER *cipher = ciphers[i].cipher_func();
                    benchmark_cipher(cipher, ciphers[i].name, ciphers[i].key_sizes[j], ciphers[i].modes[k], data_sizes[d]);
                }
            }
        }
    }

    printf("Benchmarking completed. Results saved in %s\n", RESULTS_FILE);
    return 0;
}
