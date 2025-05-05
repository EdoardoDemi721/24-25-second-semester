
H=tf(10,[1 1.6 4])

%Point a)
%For starters, 4=wn^2, so

wn=2

%That tells us that 10=K*4, so

K=2.5


%Also, 2*wn*z=1.6, so 

z=1.6/(2*wn)


%Given that z<1, the time constant is

tau=1/(z*wn)

%Point b)

step(H); %Grafico della risposta a u(t)=1*eps(t) 

i=stepinfo(H, 'SettlingTimeThreshold', 0.05)

%From the resulting image, we recognise that

yinf=2.5 %This is actually K

%for s^, we need the maximum value

ymax=3.13;

%then

s=(ymax-yinf)/yinf*100

%The relative peak time ts is the time instant for which we get ymax, so

ts=1.73

%The rise time is the first time instant at which we reach yinf, so

tr=1.09

%For the 5% settling time, we need to compute the +-5% band:

yp=yinf*1.05
ym=yinf*0.95

%The first local minimum is 2.34, which is less than 2.37. The next
%relative maximum is 2.54, which is less than 2.65. So the 5% settling time
%will be the first time instant at which we reach 2.37 after the relative
%minimum

t5p=3.8

%Fun fact: you can use stepinfo(H, 'SettlingTimeThreshold', 0.05)


