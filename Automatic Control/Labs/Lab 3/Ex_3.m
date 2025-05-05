%From the graph, we can get

yinf=1;

%Knowing that the step input has amplitude 5, that means

%Step input of amplitude U, yinf=U*K

K=yinf/5

%The ymax is about 1.155, so the max overshoot is

s=(1.155-yinf)/yinf;

%s=S*100=(ymax-yinf)/yinf

%The relative peak time is about

tp=0.45;

%The rise time is 

tr=0.3;

%Given the values, we can compute

z=(abs(log(s)))/(sqrt(pi^2+log(s)^2))
z=round(z,1)

wn=(pi-acos(z))/(tr*sqrt(1-z^2))

wn_peak_time=pi/(tp*sqrt(1-z^2))

%So the transfer function is

H=tf(K*wn^2, [1 2*z*wn wn^2])