clc; clear all;

A=[-2 0 0; 0 0 2; 0 0 0];
v=eig(A)
u=roots(minpoly(A))

%The system is internally stable because all eigen values have real part
%lesser or equal than zero and the minimal molteplicity is 1

syms t;
disp("Natural modes: ")
for i = 1:length(u)
    t^(sum(u(i)==v)-1)*exp(u(i) * t)
end
%The first natural mode is convergent, the second natural mode is divergent
%The second natural mode is t so it's divergent

%Having a divergent natural mode, the system can't be BIBO stable