function dydt = SIR(t, x, c, i, alpha, beta, gamma)
    dydt = [0; 0; 0; 0; 0];
    S = x(1);
    I = x(2);
    R = x(3);
    H = x(4);
    RH = x(5);
    N = S + I + R + H + RH;

    dydt(1) = (-c*S) * (I/N) * i;
    dydt(2) = (c*S) * (I/N) * i - (alpha*I);
    dydt(3) = (alpha*I) - (beta*R);
    dydt(4) = (beta*R) - (gamma*H);
    dydt(5) = gamma*H;
end
