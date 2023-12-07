function d = roll_2_dice(N, seed)
  % function to roll 2 dice and return the combination of each device row in a vector
  rng(seed);

  % generating two 1 * N vectors of 6 simulated dice rolls and adding them
  d = randi([1 6], 1, N) + randi([1 6], 1, N);
end
