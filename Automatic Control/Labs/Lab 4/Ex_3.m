H=zpk([],[0,0,0],10);

%% Bode plot

bode(H);

%% Nyquist

omega=sort([inf, 0, linspace(0,3,7)]);
[mag, phase, w]=bode(H, omega);

disp('omega | Re(H(jw)) | Im(H(jw))');
disp('------|-----------|-----------');
for k = 1:length(w)
    Re = mag(k) * cosd(phase(k)); % cosd() usa gradi
    Im = mag(k) * sind(phase(k)); % sind() usa gradi
    fprintf('%5.2f | %8.4f  | %8.4f\n', w(k), Re, Im);
end

nyquist(H);

%% Nichols diagram

nichols(H);