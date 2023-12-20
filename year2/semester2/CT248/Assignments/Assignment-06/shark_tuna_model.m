function dydt = shark_tuna_model(t,x)
% function to model the prey-predator population relations over time of
% sharks & tuna 

    global k;   % mentioning global variable x so that it can be used here 

    dydt = [0; 0]; 
    
    % dS/dt = k1 S T - k2 S
    dydt(1) = k(1)*x(1)*x(2)-k(2)*x(1); 
    
    % dT/dt = k3 T -k4 S T
    dydt(2) = k(3)*x(2) - k(4) * x(1) * x(2); 
end