clc; clear all;

A = [-0.2 -1; 1 0];
B = [0.5; 0];
C = [0 1];
D=0;

x0 = [0;0];

%Reachability test
Mr = ctrb(A,B);
rho_Mr = rank(Mr)

% n = dimension of A
% Since n = 2 = rho_Mr --> we can define K

% The eigenvalues are the poles of the system we want to design. We find
% the poles from the requirements, in this case overshoot and 2% settling
% time

s=0.06;
t2p=2;

z=abs(log(s))/(sqrt(pi^2+(log(s))^2))

wn=log(100/2)/(t2p*z)

%The poles are p1,2=-z*wn+-j*wn*sqrt(1-z^2)

p1=-z*wn+i*wn*sqrt(1-z^2)
p2=-z*wn-i*wn*sqrt(1-z^2)

%These poles are the desired eigenvalues

lambda_des = [ p1 p2];
K = acker (A, B, lambda_des )

%We want a system u(t)=-Kx(t)+Nr(t)

%Requirements are dcgain=gain_wanted

gain_wanted=1;

sys_cont_c = ss(A-B*K , B, C, 0);
N = gain_wanted / dcgain ( sys_cont_c )

%In general, the new system has matrices

A=A-B*K
B=B*N
C=C-D*K
D=D*N

s=tf('s');

U=1/s;

tol=1e-3;

X_zs=zpk(minreal((s*eye(2)-A)^(-1)*B*U,tol))

l=eig(A)

Y=C*X_zs

[num_Y, den_Y]=tfdata(Y,'v');
[r,p]=residue(num_Y, den_Y)

syms t;
y3=r(3)*exp(p(3)*t)

amp=2*abs(r(1))
esp=-1.9560
phi=angle(r(1))
w=2.1842

%y1=amp*exp(esp*t)*cos(phi+wt)






