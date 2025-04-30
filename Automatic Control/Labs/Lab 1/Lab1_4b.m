A=[0 1 0 0; -1 0 0 0; 3 0 0 0; 0 0 2 0]
B=[1;0;0;0]
C=[1 0 0 0]

s=tf('s');

U=1/(s^2 + 1);

tol=1e-3;

X_zs=zpk(minreal((s*eye(4)-A)^(-1)*B*U,tol))

l=eig(A)

Y=C*X_zs

[num_Y, den_Y]=tfdata(Y,'v');
[r,p]=residue(num_Y, den_Y)

y=2*abs(r(1))