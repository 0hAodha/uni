clear;

A = [1 2 ; 3 4 ];
B = eye(2);

% MATRIX multiplication
C = A*B;

% element-wise multiplication
D = A.* B;

rng(100);
rolls = randi([1 6], 1, 10);

% checking each element of rolls one by one to see which ones are greater than 3
gt3 = rolls > 3;
rolls(gt3); % filtering rolls to find the values > 3

A ~= 3;

% remove duplicates from rolls
unique(rolls)

for i = rolls
  disp(i)
 end
