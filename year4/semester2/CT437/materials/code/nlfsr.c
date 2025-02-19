//
// Example for a 16-bit NFLSR
//

#include <stdio.h>
#include <stdint.h>
#include <string.h>

#define NLFSR_SIZE  16
#define DEFAULT_START_STATE 0xACE1  // Default initial state for NLFSR

uint16_t nlfsr;
uint8_t used[65535];

uint8_t non_linear_feedback(uint16_t state) {
  // Example non-linear function: XOR of specific bits and AND of others
  uint8_t bit1 = (state >> 0) & 1;
  uint8_t bit2 = (state >> 2) & 1;
  uint8_t bit3 = (state >> 3) & 1;
  uint8_t bit4 = (state >> 5) & 1;
  uint8_t bit5 = (state >> 9) & 1;
  return (bit1 ^ bit2) | (bit3 & bit4) ^ bit5;
}

uint8_t clock_and_shift_nlfsr() {
  uint8_t ret = nlfsr & 1;
  uint8_t new_bit = non_linear_feedback(nlfsr);
  nlfsr = (nlfsr >> 1) | (new_bit << (NLFSR_SIZE - 1));

  return ret;
}

int main() {
  uint16_t input;
  uint16_t cycle = 0;
  memset(used, sizeof(used), 0);

  printf("Enter IV for NLFSR: "); scanf("%hd", &input);
  input = input & 0xFFFF;
  nlfsr = input;

  while (1) {
    cycle++;
    printf("%d", clock_and_shift_nlfsr());
    if (used[nlfsr] > 0)
      break;
    else
      used[nlfsr] = 1;
    // printf("%d\n", nlfsr);
  }

  printf("\nCycle length: %d bits.\n", cycle);
  return 0;
}

