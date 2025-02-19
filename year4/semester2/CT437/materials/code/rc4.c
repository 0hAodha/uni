//
// Example implementation of RC4
//

#include <stdio.h>
#include <stdint.h>
#include <string.h>

#define A_LEN   256
#define SWAP(a, b) { uint8_t temp = a; a = b; b = temp; }

void rc4_init(uint8_t *key, uint8_t *S, int key_length) {
    int i, j = 0;
    for (i = 0; i < 256; i++) {
        S[i] = i;
    }
    for (i = 0; i < 256; i++) {
        j = (j + S[i] + key[i % key_length]) % 256;
        SWAP(S[i], S[j]);
    }
}

void rc4_generate_keystream(uint8_t *S, uint8_t *keystream, int length) {
    int i = 0, j = 0, k;
    for (k = 0; k < length; k++) {
        i = (i + 1) % 256;
        j = (j + S[i]) % 256;
        SWAP(S[i], S[j]);
        keystream[k] = S[(S[i] + S[j]) % 256];
    }
}

void rc4_encrypt_decrypt(uint8_t *data, uint8_t *keystream, int length) {
    for (int i = 0; i < length; i++) {
        data[i] ^= keystream[i];
    }
}

int main() {
    uint8_t key[A_LEN];
    uint8_t S[A_LEN];
    uint8_t data[A_LEN];
    int data_length;
    uint8_t keystream[A_LEN];

    printf("Enter key: "); scanf("%s", key);
    printf("Enter plaintext: "); scanf("%s", data);
    data_length = strlen((char *)data);

    rc4_init(key, S, strlen((char *)key));
    rc4_generate_keystream(S, keystream, data_length);
    rc4_encrypt_decrypt(data, keystream, data_length);

    printf("Encrypted: ");
    for (int i = 0; i < data_length; i++) {
        printf("%02X ", data[i]);
    }
    printf("\n");

    // Decrypting the data
    rc4_encrypt_decrypt(data, keystream, data_length);

    printf("Decrypted: %s\n", data);

    return 0;
}

