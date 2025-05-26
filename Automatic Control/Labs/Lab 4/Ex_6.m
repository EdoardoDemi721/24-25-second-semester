clc;
clear;

%% Functions definition in part 1

l=0.000186;
k=0.005;
c=3750;
N=60;
R=0.246;


s=tf('s');

q=(c^2/2*N);
p=(s+2*N/c*R^2)*(s+1/R);

G=q/p;

[numG, denG]=tfdata(G, 'v');

C=l/(1+s/k);

[numC, denC]=tfdata(C, 'v');



%% Simulink

load_system('untitled4.slx');

simout=sim('untitled4.slx');

plot(simout);

%% Prova plot

L=C*G;

T=L/(1+L);

plot(step(T));

