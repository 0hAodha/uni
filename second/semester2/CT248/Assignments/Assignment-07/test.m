clear; 

i = 0.125; 
alpha = 0.25; 
beta = 0.02; 
gamma = 0.10; 

time_vec = 0:.25:100;
init_vec = [9999 1 0 0 0];
c = linspace(3, 8, 20); 

infected_stock = zeros(length(time_vec), 20); 
in_hospital = zeros(length(time_vec), 20); 

for loopcounter = 1:20
    [t,y] = ode45(@SIR, ...
                   time_vec, ...
                   init_vec, ...
                   odeset, ...
                   c(loopcounter), ...
                   i, ...
                   alpha, ...
                   beta, ...
                   gamma); 
    
    infected_stock(:, loopcounter) = y(:,2); 
    in_hospital(:,loopcounter) = y(:,4); 
end

subplot(3, 1, 1);
plot(time_vec, infected_stock); 
title("Infected Stock"); 

subplot(3, 1, 2);
plot(time_vec, infected_stock); 
title("People in Hospital"); 

subplot(3,1,3); 
scatter(c, max(in_hospital)); 
title("Contacts v Peak in Hospital");
