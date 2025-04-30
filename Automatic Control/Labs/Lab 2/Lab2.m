clc; clear all;

A=[0 1; -1 -1];
v=eig(A)
v=roots(minpoly(A))


%a) The eigen values all have strictly negative real part, so the system is
%asymptotically stable

%b) The natural modes are of the kind e^(-0.5t)cos(...), so they are
%convergent

%c) The system is BIBO stable because it's asymptotically stable

%d) The constant factor outside of the matrix results in a multiplication
%by the same constant factor for all eigenvalues. Being said factor 1/3,
%all eigenvalues will mantain the same sign and so the system will have the
%same stability

%e) the time constant of the systems:

p1=[1/abs(real(v(1))) 1/abs(real(v(2)))]
p2=3 * p1
