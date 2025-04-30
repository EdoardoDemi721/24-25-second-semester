clc; clear all;

A=[1 3; 6 4];
v=eig(A)

%a) The system is not stable because one of the eigenvalues of A has
%positive real part
syms t;
disp("Natural modes: ")
for i = 1:length(v)
    exp(v(i) * t)
end
%The first natural mode is convergent, the second natural mode is divergent
%The system is not stable hence it can't be BIBO stable

