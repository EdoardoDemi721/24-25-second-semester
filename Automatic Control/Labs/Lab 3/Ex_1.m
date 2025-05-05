%Point a)

H=tf([1], [1 2 5.25 4.25])
p=pole(H)

%The poles all have strictly negative real part, thus the system is BIBO
%stable

%Point b)

%Is it possible to compute the ss response? Yes, because the input is bounded and
%the system is BIBO stable

%u(t)= (3sin(0.1)t+2)eps(t)

%U(t) = 3sin(0.1)t*eps + 2*eps

%For the 2 step, we just multiply by the gain H(0)

y1=double(2*dcgain(H))

%For the sinusoidal input, we have to compute amplitude and phase of the
%response, which we can do via the bode function

[amp, phi] =bode(H, 0.1); %H is the tf, 0.1 is the frequency of the input

amp=round(3*amp, 3)
phi=deg2rad(phi) %Bode gives phase in degrees!!!

%point c)

%A ss response to such input will be of the kind u*gain*sin(wt+phi)
%To satisfy the boundary on the module, we just need |u*gain|<1. We can
%study when the amplitude is 1

[amp, phi] = bode(H, 3);

u=1/amp %17.7658

%So we need u<=17.7658



