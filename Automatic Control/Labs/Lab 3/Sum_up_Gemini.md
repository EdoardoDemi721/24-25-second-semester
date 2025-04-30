- **BIBO stability of LTI systems**
    
    A SISO LTI system is **BIBO** stable if the zero state output response is bounded for **all** bounded inputs:
    
    $\forall u_M\in(0,\infty), \exist y_m\in(0, \infty):|u(t)| \leq u_M, \forall t \geq 0\implies |y(t)|\leq y_M, \forall t \geq 0$
    
    **Practical result:** An LTI system is **BIBO stable** if and only if all the poles of the transfer function have **strictly negative** real parts.
    
    **Notice**: Asymptotical stability **implies** BIBO stability, so: if $\text{Re}[\lambda_i(A)] < 0$ for $i = 1, ... , n$, then the system is also BIBO stable!
    
    - MatLab example
        
        Analyze the BIBO stability of an LTI system having the following state space representation:
        
        $\dot x(t) = \begin{bmatrix}
           -1 & 2 \\
           1 & 0
        \end{bmatrix} x(t) + \begin{bmatrix}
           2 \\
           0
        \end{bmatrix} u(t) \\
        y(t)= \begin{bmatrix}
           0.5 & -0.5
        \end{bmatrix} x(t)$
        
        ```matlab
        A = [-1 2; 1 0];
        B = [2; 0];
        C = [0.5 -0.5];
        D = 0;
        
        s = tf('s');
        
        % Compute the H transfer function
        sys = ss(A, B, C, D);
        H = tf(sys);
        H = zpk(minreal(H, 1e-2))
        
        % Compute the poles of H and check the real part.
        pole(H)
        ```
        
- **Steady state response**
    - Step input
        
        The steady state response $y_{ss}(t)$ due to a step input signal of amplitude $\bar u$: $u(t)=\bar u \epsilon(t)$ is given by $y_{ss}(t) = \bar y \epsilon(t)$, where $\bar y = \bar u \cdot H(0)$
        
        - MatLab example
            
            Compute, if possible, the steady state output response of an LTI system described by the following transform function in the presence of the input $u(t)= 2\epsilon(t)$
            
            $H(s)= \frac{1}{s^3+2s^2+5.25s+4.25}$
            
            ```matlab
            s = tf('s');
            H = 1/(s^3 + 2*s^2 + 5.25*s + 4.25);
            H = zpk(minreal(H, 1e-2))
            poles = pole(H)
            u_bar_step = 2;
            K = dcgain(H) * u_bar_step
            ```
            
            Firstly we need to check if we **can** compute the steady state response (if the system is internally stable), all the poles of $H$ have strictly negative real part, so the system is internally stable and we can proceed.
            
            We get: $y_{ss}(t)=K\bar u \epsilon(t)=0.4706\epsilon(t)$
            
    - Sinusoidal input
        
        The steady state response $y_{ss}(t)$ due to a sinusoidal input signal of shape $u(t) = \bar u\sin(\omega_0 t)$  is given by $y_{ss}(t) = \bar y(\omega_0)\sin(\omega_0 t + \phi(\omega_0))$, where:
        
        - $\bar y (\omega_0) = \bar u \cdot |H(j\omega _0)|$
        - $\phi(\omega_0)=\arg(H(j\omega_0))$
        - MatLab example
            
            Compute, if possible, the steady state output response of an LTI system described by the following transform function in the presence of the input $u(t)= 3\sin(0.1t)\epsilon(t)$
            
            ```matlab
            s = tf('s');
            H = 1/(s^3 + 2*s^2 + 5.25*s + 4.25);
            H = zpk(minreal(H, 1e-2))
            poles = pole(H)
            u_bar_sin = 3;
            w0 = 0.1;
            [mag, phi] = bode(H, w0)
            
            % We should convert from degrees to radiants
            phi_rad = phi / 180*pi
            y_bar = mag * u_bar_sin
            ```
            
            Firstly we need to check if we **can** compute the steady state response (if the system is internally stable), all the poles of $H$ have strictly negative real part, so the system is internally stable and we can proceed.
            
            We get: $y_{ss}(t) = \bar y \sin(\omega_0t+\phi)\epsilon(t)=0.7038\sin(0.1t-0.1232)\epsilon(t)$
            
- **Analysis of the step response of prototype $1^{\text{st}}$and $2^{\text{nd}}$ order systems**
    - First order system
        
        Consider a first order system described by the transfer function $H(s)=\frac{K^*}{s-p}$, and let 
        $\tau = |\frac{1}{p}|$ and $K=-\frac{K^*}{p}$.
        We can rewrite $H(s)=\frac{K}{1+\tau s}$.
        
        In the presence of a step input $u(t)$ with amplitude $\bar u$ the output response can be written as:
        $y(t)=\bar u K(1-e^{-\frac{t}{\tau}})$, for $t\geq 0$.
        
        ![Graphical course of y(t)](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cfd404fd-05d6-4d5e-87db-c17056846b26/Screenshot_2022-12-29_at_23.21.39.png)
        
        Graphical course of y(t)
        
        We can compute its characteristics:
        
        - **Steady state value**: $y_\infty=\lim_{t\to\infty}y(t)=K\cdot\bar u$
        - **10%-90% rise time: $t'_r$** is the time required to the step response to go from the 10% to the 90% of the steady state value $y_\infty$
            
            ![Screenshot 2022-12-29 at 23.13.08.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0cb99a11-49d0-41e8-8407-57037f9c6622/Screenshot_2022-12-29_at_23.13.08.png)
            
        - **Settling time $\pm\alpha \%$: $t_{s, \alpha \%}$** is the amount of time required to the step response to reach and stay within the $\pm \alpha\%$ of the $y_\infty$
    - Second order system
        
        Consider a stable second order system described by the transfer function:
        $H(s)=K\frac{1}{1+s\frac{\zeta}{\omega_n}s + \frac{s^2}{\omega_n^2}}=K\frac{\omega_n^2}{s^2+2\zeta\omega_ns+\omega_n^2}$ and let $\tau = \frac{1}{\zeta\omega_n}$.
        
        In the presence of a step input $u(t)$ with amplitude $\bar u$ , the output response can be written as:
        $y(t)=\bar u K (1-\frac{1}{\sqrt{1-\zeta^2}}e^{-\zeta\omega_nt}\sin(\omega_n\sqrt{1-\zeta^2t}+\arccos(\zeta)))$, for $t\geq 0$
        
        ![Graphical course of y(t)](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9b7ed1f5-d26f-4312-8195-ae20d368fa19/Screenshot_2022-12-29_at_23.22.22.png)
        
        Graphical course of y(t)
        
        We can compute its characteristics:
        
        - **Steady state value**: $y_\infty=\lim_{t\to\infty}y(t)=K\cdot\bar u$
        - **Peak value**: $y_{max}=\max(y(t))$
        - **Peak time**: $\hat t$ is the time instant when $y(t)$ reaches $y_{max}$.
        $\hat t = \frac{\pi}{\omega_n\sqrt{1-\zeta^2}}$
            
            ![Screenshot 2022-12-29 at 23.25.32.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9176f126-1d62-4ce0-856c-99f818688c6f/Screenshot_2022-12-29_at_23.25.32.png)
            
        - **Maximum overshoot**: $\hat s=\frac{y_{max}-y_{\infty}}{y_{\infty}}$
        $\hat s = e^{-\frac{\pi\zeta}{\sqrt{1-\zeta^2}}}$
            
            ![Screenshot 2022-12-29 at 23.27.20.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2b12eaa1-257c-422a-ac23-f83b54f9b14f/Screenshot_2022-12-29_at_23.27.20.png)
            
        - **Rise time**: $t_r$ is the time required to the step response to reach for the first time the steady state value $y_\infty$
        $t_r=\frac{1}{\omega_n\sqrt{1-\zeta^2}}(\pi-\arccos(\zeta))$
            
            ![Screenshot 2022-12-29 at 23.29.09.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0e1467b5-4941-4156-9aa7-cd525ffd9e47/Screenshot_2022-12-29_at_23.29.09.png)
            
        - **10%-90% rise time**: $t'_r$ is the time required to the step response to go from $10\%$ to the $90\%$ of $y_\infty$
        $t'_r=\frac{2.16\zeta+0.6}{\omega_n}$
            
            ![Screenshot 2022-12-29 at 23.33.49.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7afe31ca-7efd-405a-887a-7d4a9db54db3/Screenshot_2022-12-29_at_23.33.49.png)
            
        - **Settling time $\pm \alpha\%$: $t_{s, \alpha\%}$** is the amount of time required to the step response to reach and stay within the $\pm \alpha \%$ of $y_\infty$
            
            ![Screenshot 2022-12-29 at 23.30.48.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a5bf638f-bc06-455e-ac6f-e9eef4eddeb7/Screenshot_2022-12-29_at_23.30.48.png)
            
        - Parameters formulas
            
            $\zeta=\frac{|\ln(\hat s)|}{\sqrt{\pi^2+\ln^2(\hat s)}}$
