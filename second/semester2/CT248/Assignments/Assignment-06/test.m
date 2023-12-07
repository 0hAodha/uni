clear; 

global k; 

k = [0.015 0.7 0.5 0.01].'; 

[t,y] = ode45(@shark_tuna_model, [0 50], [100 100]);
plot(t,y); 
title("SHARK-TUNA POPULATION DYNAMICS LIMIT CYCLING");
xlabel("TIME"); 
ylabel("POPULATION NUMBERS");
legend("SHARKS", "TUNA", "Location", "northwest");
