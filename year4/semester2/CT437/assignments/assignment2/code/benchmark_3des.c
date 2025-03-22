#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/evp.h>
#include <sys/time.h>
#include <sys/resource.h>

#define DATA_SIZE_100MB  (100 * 1024 * 1024)
#define DATA_SIZE_1000MB (1000 * 1024 * 1024)
#define RESULTS_FILE_3DES "3des_results.tsv"

// Utility to measure CPU time
double get_cpu_time() {
    struct rusage usage;
    getrusage(RUSAGE_SELF, &usage);
    return (double) usage.ru_utime.tv_sec + (double) usage.ru_utime.tv_usec / 1e6;
}

// Encrypt or decrypt data using a given EVP_CIPHER
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

void benchmark_3des_mode(const EVP_CIPHER *cipher, const char *mode, int data_size) {
    unsigned char key[24] = {0}; // 3DES uses 192 bits = 24 bytes key
    unsigned char iv[8] = {0};   // 3DES block size = 64 bits = 8 bytes
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

    // Encryption
    start = get_cpu_time();
    encrypt_decrypt(cipher, key, iv, input, output, 1, data_size);
    end = get_cpu_time();
    double enc_time = end - start;

    // Decryption
    start = get_cpu_time();
    encrypt_decrypt(cipher, key, iv, output, input, 0, data_size);
    end = get_cpu_time();
    double dec_time = end - start;

    // Write to results file
    FILE *file = fopen(RESULTS_FILE_3DES, "a");
    if (!file) {
        fprintf(stderr, "Failed to write to %s\n", RESULTS_FILE_3DES);
        free(input);
        free(output);
        return;
    }

    fprintf(file, "TripleDES\t192\t%s\t%d\t%.6f\t%.6f\n", mode, data_size / (1024 * 1024), enc_time, dec_time);
    fclose(file);

    free(input);
    free(output);
}

int main() {
    FILE *file = fopen(RESULTS_FILE_3DES, "w");
    if (!file) {
        fprintf(stderr, "Failed to create file %s\n", RESULTS_FILE_3DES);
        return 1;
    }
    fprintf(file, "Cipher\tKey Size\tMode\tData Size (MB)\tEncryption Time (s)\tDecryption Time (s)\n");
    fclose(file);

    int data_sizes[] = {DATA_SIZE_100MB, DATA_SIZE_1000MB};

    for (int i = 0; i < 2; i++) {
        benchmark_3des_mode(EVP_des_ede3_ecb(), "ECB", data_sizes[i]);
        benchmark_3des_mode(EVP_des_ede3_cbc(), "CBC", data_sizes[i]);
    }

    printf("3DES Benchmarking completed. Results saved in %s\n", RESULTS_FILE_3DES);
    return 0;
}
