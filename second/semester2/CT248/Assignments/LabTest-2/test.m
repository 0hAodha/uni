clear;

r = 0.2; % random val for r
K = linspace(1000, 1000000, 50);
time_vec = linspace(0, 100, 50);
P = 100; % random val for P
population = zeros(length(time_vec), 50);

% implementing the model as an anonymous function 
dpdt = @(t,P,r,K) r*P * (1 - (P/K)); 

for loopcounter = 1:50
    [t,y] = ode45(dpdt, ...
                  time_vec, ...
                  P, ...
                  odeset, ...
                  r, ...
                  K(loopcounter));
    population(:, loopcounter) = y(:,1); 
end

plot(time_vec, population);
