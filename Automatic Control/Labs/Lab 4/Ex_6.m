clc

%% Functions definition in part 1

l=0.000186;
k=0.005;
c=3750;
N=60;
R=0.246;


s=tf('s');

q=(c^2/2*N)*exp(-s*R);
p=(s+2*N/c*R^2)*(s+1/R);

numG=q;
denG=p;

numC=l;
denC=1+s/k;

