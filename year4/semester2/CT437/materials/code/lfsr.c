//
// Example for a 16-bit NFLSR
//

#include <stdio.h>
#include <stdint.h>
#include <string.h>

#define LFSR_SIZE  16
#define DEFAULT_START_STATE 0xACE1  // Default initial state for NLFSR

uint16_t lfsr;
uint8_t used[65535];

uint8_t non_linear_feedback(uint16_t state) {
  // Example non-linear function: XOR of specific bits and AND of others
  uint8_t bit1 = (state >> 0) & 1;
  uint8_t bit2 = (state >> 2) & 1;
  uint8_t bit3 = (state >> 3) & 1;
  uint8_t bit4 = (state >> 5) & 1;
  uint8_t bit5 = (state >> 9) & 1;
  return (bit1 ^ bit2 ^ bit3 ^ bit4 ^ bit5);
}

uint8_t clock_and_shift_lfsr() {
  uint8_t ret = lfsr & 1;
  uint8_t new_bit = non_linear_feedback(lfsr);
  lfsr = (lfsr >> 1) | (new_bit << (LFSR_SIZE - 1));

  return ret;
}

int main() {
  uint16_t input;
  uint16_t cycle = 0;
  memset(used, sizeof(used), 0);

  printf("Enter IV for LFSR: "); scanf("%hd", &input);
  input = input & 0xFFFF;
  lfsr = input;

  while (1) {
    cycle++;
    printf("%d", clock_and_shift_lfsr());
    if (used[lfsr] > 0)
      break;
    else
      used[lfsr] = 1;
    // printf("%d\n", nlfsr);
  }

  printf("\nCycle length: %d bits.\n", cycle);
  return 0;
}

