#ifndef BENCHMARK_H
#define BENCHMARK_H

#include <openssl/evp.h>
#include <sys/resource.h>

#define DATA_SIZE_100MB  (100 * 1024 * 1024)
#define DATA_SIZE_1000MB (1000 * 1024 * 1024)
#define AES_BLOCK_SIZE   16
#define RESULTS_FILE     "results.tsv"

// Function prototypes
double get_cpu_time();
int encrypt_decrypt(const EVP_CIPHER *cipher, unsigned char *key, unsigned char *iv, unsigned char *input, unsigned char *output, int encrypt, int data_size);
void benchmark_cipher(const EVP_CIPHER *cipher, char *cipher_name, int key_size, char *mode, int data_size);

#endif // BENCHMARK_H
