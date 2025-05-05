H=zpk([+j, -j],[2, -2, -4],1);

%% Bode plot

bode(H);

%% Nyquist

omegalog=linspace(0, 5, 11);
omega=sort([omegalog, inf, linspace(5, 10, 5)]);
[mag, phase, w]=bode(H, omega);

disp('omega | Re(H(jw)) | Im(H(jw))');
disp('------|-----------|-----------');
for k = 1:length(w)
    Re = mag(k) * cosd(phase(k)); % cosd() usa gradi
    Im = mag(k) * sind(phase(k)); % sind() usa gradi
    fprintf('%5.2f | %8.4f  | %8.4f\n', w(k), Re, Im);
end

nyquist(H);

%% Nihcols

nichols(H);