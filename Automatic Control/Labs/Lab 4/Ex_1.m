%% Transfer functions

H1=zpk([], [-1, 1, 1], -1)
H2=zpk([], [0, 0, 2, 2], 10)
H3=zpk([1], [0, 3, -3], 9)

%% H1 plots

figure; bode(H1); title('H1 Bode diagram');

omega1log=logspace(-1, 1, 7);
omega1=sort([omega1log, 0, 1, inf]);
[mag, phase, w]=bode(H1, omega1);

disp('omega | Re(H(jw)) | Im(H(jw))');
disp('------|-----------|-----------');
for k = 1:length(w)
    Re = mag(k) * cosd(phase(k)); % cosd() usa gradi
    Im = mag(k) * sind(phase(k)); % sind() usa gradi
    fprintf('%5.2f | %8.4f  | %8.4f\n', w(k), Re, Im);
end

nyquist(H1);
nichols(H1);

%% H2 plots

figure; bode(H2); title('H2 Bode diagram');

omega2log=linspace(0, 2, 11);
omega2=sort([omega2log, inf]);
[mag, phase, w]=bode(H2, omega2);

disp('omega | Re(H(jw)) | Im(H(jw))');
disp('------|-----------|-----------');
for k = 1:length(w)
    Re = mag(k) * cosd(phase(k)); % cosd() usa gradi
    Im = mag(k) * sind(phase(k)); % sind() usa gradi
    fprintf('%5.2f | %8.4f  | %8.4f\n', w(k), Re, Im);
end

nyquist(H2);
nichols(H2);

%% H3 plots

figure; bode(H3); title('H3 Bode diagram');

omega3log=linspace(0, 4, 9);
omega3=sort([omega3log, inf]);
[mag, phase, w]=bode(H3, omega3);

disp('omega | Re(H(jw)) | Im(H(jw))');
disp('------|-----------|-----------');
for k = 1:length(w)
    Re = mag(k) * cosd(phase(k)); % cosd() usa gradi
    Im = mag(k) * sind(phase(k)); % sind() usa gradi
    fprintf('%5.2f | %8.4f  | %8.4f\n', w(k), Re, Im);
end

nyquist(H3);
nichols(H3);





