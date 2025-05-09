G=zpk([],[-1,-1],1)
C=zpk([-1,-1],[0,-4,-4],1/16)

U=zpk([-1,-1],[-2,-2,0],4)

%% Residues computation (Qst. 1)

[numU, denU] = tfdata(U,'v');

[r,p]=residue(numU, denU)

%% Qst. 2-3

T=tf([1],[1/4, 1, 1])

%The system is well posed

[p]=pole(T)

%The closed loop system has a double negative pole, so it is stable (actually BIBO
%stable)

%% Nyquist critherion

L=tf([1],[1/4, 1, 0]);

[l]=pole(L);
l=(real(l)>0);
P=length(l);

Z=length(p);

nyquist(L)

N=0; %from nyquist(L)

disp(Z)
disp(P+N)


%The critherion gives 2=2, which is true, confirming the stability

%% Qst. 5

%The steady state responses are possible to compute, because the system is
%BIBO stable

H=-tf([1/4 1 0],[1/4 1 1]);

[amp, phase]=bode(H, 1);

phase=deg2rad(phase);

disp([amp,phase-2*pi])

%% Qst. 6

clc

S=-H;

As=dcgain(S)

At=dcgain(T)
