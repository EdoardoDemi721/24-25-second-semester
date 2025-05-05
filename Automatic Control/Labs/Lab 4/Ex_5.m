H=zpk([50],[0, 5, 5], -1/2);

U=tf(2,[1 0]);

Y=U*H

[Ynum, Yden] = tfdata(Y, 'v');

[r, p] = residue(Ynum, Yden)

