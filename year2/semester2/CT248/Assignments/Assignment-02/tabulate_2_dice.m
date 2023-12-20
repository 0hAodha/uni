function [freq, prop] = tabulate_2_dice(d)
  % function to calculate the frequency and proportion of each outcome based on a set of dice throws
  freq = zeros(1,12);
  % looping through all the values in d and incrementing the corresponding counter in freq
  for i = d
    freq(i) = freq(i) + 1;
  end

  % looping through each index in prop and calculating the proportion pertaining to that index
  prop = zeros(1,12);
  for i = [1:12]
    prop(i) = freq(i) / sum(freq); % proportion of i is the frequency of i divided by the sum of all freqs
  end
end
