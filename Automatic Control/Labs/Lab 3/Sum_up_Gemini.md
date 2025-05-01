Ecco un riassunto degli appunti forniti, focalizzato sugli aspetti chiave per il Laboratorio 3:

### Riassunto Appunti in Ottica Lab 3

1.  **Stabilità BIBO (Bounded-Input Bounded-Output)**
    * **Concetto Chiave:** Un sistema LTI è BIBO stabile se e solo se tutti i poli della sua funzione di trasferimento $H(s)$ hanno parte reale *strettamente negativa*.
    * **Rilevanza per Lab 3 (Problema 1a):** Dovrai trovare i poli di $H(s)$ e verificare che la loro parte reale sia $< 0$ per determinare la stabilità. Gli appunti mostrano come farlo in MATLAB usando `pole(H)`.
    * **Nota:** La stabilità asintotica (autovalori della matrice A con parte reale negativa) implica la stabilità BIBO.

2.  **Risposta a Regime Stazionario ($y_{ss}(t)$)**
    * **Prerequisito:** Si può calcolare solo per sistemi BIBO stabili.
    * **Ingresso a Gradino:** Per $u(t) = \bar{u}\epsilon(t)$, la risposta a regime è costante: $y_{ss}(t) = \bar{y}\epsilon(t)$, dove $\bar{y} = H(0) \cdot \bar{u}$. Il valore $H(0)$ è il guadagno statico.
        * **Rilevanza per Lab 3 (Problema 1b):** Per la componente costante dell'ingresso ($+2\epsilon(t)$), userai questa formula. Gli appunti mostrano come calcolare $H(0)$ (guadagno DC) in MATLAB con `dcgain(H)`.
    * **Ingresso Sinusoidale:** Per $u(t) = \bar{u}\sin(\omega_0 t)\epsilon(t)$, la risposta a regime è $y_{ss}(t) = \bar{y}(\omega_0)\sin(\omega_0 t + \phi(\omega_0))\epsilon(t)$.
        * Ampiezza: $\bar{y}(\omega_0) = \bar{u} \cdot |H(j\omega_0)|$
        * Fase: $\phi(\omega_0) = \angle H(j\omega_0)$
        * **Rilevanza per Lab 3 (Problema 1b, 1c):** Dovrai calcolare modulo $|H(j\omega_0)|$ e fase $\angle H(j\omega_0)$ della funzione di risposta armonica $H(j\omega)$ alla pulsazione $\omega_0$ dell'ingresso. Gli appunti mostrano come ottenere modulo (`mag`) e fase (`phi`) in MATLAB con `bode(H, w0)`. Ricorda di convertire la fase da gradi a radianti se necessario ($\phi_{rad} = \phi \cdot \pi / 180$).

3.  **Analisi Risposta al Gradino (Sistemi del 2° Ordine)**
    * **Funzione di Trasferimento Standard:** $H(s) = K\frac{\omega_n^2}{s^2+2\zeta\omega_n s+\omega_n^2}$. Parametri chiave: $K$ (guadagno statico), $\omega_n$ (pulsazione naturale), $\zeta$ (smorzamento).
    * **Rilevanza per Lab 3 (Problema 2a, Problema 3):** Devi saper identificare $K$, $\omega_n$, $\zeta$ dalla $H(s)$ data (Problema 2a) o ricavarli dall'analisi della risposta al gradino (Problema 3).
    * **Caratteristiche della Risposta al Gradino (per $0 < \zeta < 1$):**
        * **Valore a Regime ($y_\infty$):** $y_\infty = K \cdot \bar{u}$ (dove $\bar{u}$ è l'ampiezza del gradino).
        * **Sovraelongazione Massima ($\hat{s}$):** $\hat{s} = \frac{y_{max} - y_{\infty}}{y_{\infty}} = e^{-\frac{\pi\zeta}{\sqrt{1-\zeta^2}}}$. Dipende solo da $\zeta$.
        * **Tempo di Picco ($\hat{t}$):** Istante della sovraelongazione massima. $\hat{t} = \frac{\pi}{\omega_n\sqrt{1-\zeta^2}}$.
        * **Tempo di Salita ($t_r$):** Tempo per raggiungere $y_\infty$ per la prima volta. $t_r=\frac{1}{\omega_n\sqrt{1-\zeta^2}}(\pi-\arccos(\zeta))$. (Gli appunti danno anche una formula per il $t_r'$ 10%-90%).
       * **Tempo di Assestamento ($\pm\alpha \%$):** $t_{s \quad \alpha_{perc} }$ è il tempo necessario affinché la risposta al gradino raggiunga e rimanga entro la fascia $\pm \alpha \%$ del valore a regime $y_\infty$.
    * Per $\alpha=5_{perc} $, $t_{s, 5_{perc} } \approx \frac{3}{\zeta\omega_n}$.
    * **Rilevanza per Lab 3 (Problema 2b, Problema 3):** Dovrai estrarre queste metriche ($y_\infty$, $\hat{s}$, $\hat{t}$, $t_r$, $t_{s, 5_{perc} }$) da un grafico della risposta al gradino (Problema 2b, 3) e usare le formule inverse (specialmente per $\zeta$ e $\omega_n$) per trovare i parametri del sistema (Problema 3). La formula fornita per $\zeta$ è: $\zeta=\frac{|\ln(\hat s)|}{\sqrt{\pi^2+\ln^2(\hat s)}}$ (assumendo $\hat{s}$ come rapporto, non percentuale). Una volta noto $\zeta$, puoi ricavare $\omega_n$ dalla formula di $\hat{t}$.


In sintesi, per il Lab 3 devi saper: verificare la stabilità BIBO tramite i poli, calcolare la risposta a regime per ingressi costanti e sinusoidali usando $H(0)$ e $H(j\omega)$, e analizzare la risposta al gradino dei sistemi del secondo ordine per estrarre le metriche caratteristiche e/o risalire ai parametri $K, \omega_n, \zeta$.

Ecco un elenco di funzioni MATLAB utili per svolgere gli esercizi del Laboratorio 3, basate sulle operazioni descritte negli appunti e nei problemi:

**Definizione del Sistema:**

* `tf(num, den)`: Crea un modello di funzione di trasferimento (Transfer Function) dai coefficienti del numeratore (`num`) e denominatore (`den`).
* `zpk(z, p, k)`: Crea un modello di funzione di trasferimento specificando zeri (`z`), poli (`p`) e guadagno (`k`).
* `ss(A, B, C, D)`: Crea un modello state-space (usato negli esempi degli appunti).

**Analisi del Sistema:**

* `pole(sys)`: Calcola i poli del sistema `sys` (per analisi di stabilità).
* `zero(sys)`: Calcola gli zeri del sistema `sys`.
* `isstable(sys)`: Verifica direttamente la stabilità del sistema (restituisce 1 se stabile, 0 altrimenti).
* `minreal(sys)`: Rimuove cancellazioni polo/zero non minimali.
* `dcgain(sys)`: Calcola il guadagno a frequenza zero ($H(0)$), utile per la risposta a regime al gradino.
* `bode(sys, w)`: Calcola modulo (in dB) e fase (in gradi) della risposta in frequenza $H(j\omega)$ alle pulsazioni specificate nel vettore `w`. Se `w` è uno scalare, calcola per quella specifica pulsazione. Restituisce `[mag, phase]`.
* `evalfr(sys, s)`: Valuta la funzione di trasferimento `sys` al valore complesso `s` (utile per calcolare $H(j\omega)$ direttamente come numero complesso: `H_jw = evalfr(sys, 1j*w0)`).

**Analisi Risposta al Gradino:**

* `step(sys)`: Calcola e plotta la risposta al gradino unitario del sistema `sys`.
* `step(sys, Tfinal)`: Specifica il tempo finale della simulazione.
* `[y, t] = step(sys)`: Restituisce i valori della risposta `y` e i corrispondenti istanti di tempo `t` senza plottare.
* `stepinfo(sys)`: Calcola automaticamente le caratteristiche principali della risposta al gradino (RiseTime, SettlingTime, Overshoot, Peak, PeakTime, Undershoot, SteadyStateValue). Molto utile per verificare i valori letti dal grafico o calcolati manualmente.

**Funzioni Matematiche Utili:**

* `abs()`: Valore assoluto (utile per il modulo di $H(j\omega)$ se calcolato con `evalfr`).
* `angle()`: Fase (in radianti) di un numero complesso (utile per la fase di $H(j\omega)$ se calcolato con `evalfr`).
* `real()`: Parte reale di un numero complesso (per controllare i poli).
* `log()`: Logaritmo naturale (per le formule inverse della sovraelongazione).
* `sqrt()`: Radice quadrata (per le formule di $\omega_n, \zeta, \hat{t}$, etc.).
* `pi`: Costante $\pi$.
* `acos()`: Arccos K (per la formula del tempo di salita $t_r$).

**Funzioni per Grafici:**

* `plot(t, y)`: Crea un grafico 2D usando i vettori `t` e `y`.
* `grid on`: Aggiunge una griglia al grafico corrente.
* `title('Titolo')`: Aggiunge un titolo al grafico.
* `xlabel('Etichetta Asse X')`: Aggiunge un'etichetta all'asse X.
* `ylabel('Etichetta Asse Y')`: Aggiunge un'etichetta all'asse Y.
* `hold on`/`hold off`: Permette/impedisce di sovrapporre più grafici nella stessa figura.
* `legend('Curva 1', 'Curva 2', ...)`: Aggiunge una legenda al grafico.

Questo elenco dovrebbe coprire tutte le necessità per implementare le soluzioni del Laboratorio 3 in MATLAB.

**Time constant:**

Certo, capisco che stai cercando di trovare le costanti di tempo associate ai poli di una funzione di trasferimento di un sistema del secondo ordine. Ecco come puoi fare, tenendo presente che la relazione precisa dipende dalla forma in cui è espressa la funzione di trasferimento.

**Forma Generale della Funzione di Trasferimento del Secondo Ordine:**

Una forma generale della funzione di trasferimento di un sistema del secondo ordine è:

$$G(s) = \frac{K \omega_n^2}{s^2 + 2 \zeta \omega_n s + \omega_n^2}$$

dove:
* $K$ è il guadagno statico del sistema.
* $\zeta$ (zeta) è il coefficiente di smorzamento (adimensionale).
* $\omega_n$ è la frequenza naturale non smorzata (in radianti al secondo, rad/s).

I poli del sistema sono le radici del denominatore della funzione di trasferimento, ottenute risolvendo l'equazione caratteristica:

$$s^2 + 2 \zeta \omega_n s + \omega_n^2 = 0$$

Le radici di questa equazione quadratica sono date dalla formula:

$$s_{1,2} = -\zeta \omega_n \pm \omega_n \sqrt{\zeta^2 - 1}$$

**Tre Casi Possibili per i Poli e le Costanti di Tempo:**

La natura delle radici (e quindi delle costanti di tempo) dipende dal valore del coefficiente di smorzamento $\zeta$:

1.  **Sistema Sovrasmorzato ($\zeta > 1$):**
    * In questo caso, $\zeta^2 - 1 > 0$, quindi le due radici $s_1$ e $s_2$ sono reali e distinte, entrambe negative.
    * I poli sono:
        $$s_1 = -\zeta \omega_n + \omega_n \sqrt{\zeta^2 - 1} = -\frac{1}{\tau_1}$$       $$s_2 = -\zeta \omega_n - \omega_n \sqrt{\zeta^2 - 1} = -\frac{1}{\tau_2}$$
    * Le costanti di tempo associate a questi poli sono semplicemente l'inverso del valore assoluto dei poli:
        $$\tau_1 = -\frac{1}{s_1} = \frac{1}{\zeta \omega_n - \omega_n \sqrt{\zeta^2 - 1}}$$       $$\tau_2 = -\frac{1}{s_2} = \frac{1}{\zeta \omega_n + \omega_n \sqrt{\zeta^2 - 1}}$$
    * Il sistema risponde lentamente senza oscillazioni. La risposta è una somma di due decadimenti esponenziali con queste due costanti di tempo.

2.  **Sistema Criticamente Smorzato ($\zeta = 1$):**
    * In questo caso, $\zeta^2 - 1 = 0$, quindi le due radici $s_1$ e $s_2$ sono reali e coincidenti:
        $$s_1 = s_2 = -\zeta \omega_n = -\omega_n = -\frac{1}{\tau}$$
    * C'è un solo polo reale ripetuto.
    * La costante di tempo associata a questo polo è:
        $$\tau = \frac{1}{\omega_n}$$
    * Il sistema raggiunge l'equilibrio il più rapidamente possibile senza oscillazioni. La risposta contiene un termine esponenziale e un termine lineare moltiplicato per un esponenziale ($te^{-t/\tau}$).

3.  **Sistema Sottosmorzato ($0 \le \zeta < 1$):**
    * In questo caso, $\zeta^2 - 1 < 0$, quindi le due radici $s_1$ e $s_2$ sono complesse coniugate:
        $$s_{1,2} = -\zeta \omega_n \pm j \omega_n \sqrt{1 - \zeta^2} = -\sigma \pm j \omega_d$$
        dove $\sigma = \zeta \omega_n$ è il fattore di smorzamento esponenziale e $\omega_d = \omega_n \sqrt{1 - \zeta^2}$ è la frequenza naturale smorzata.
    * In questo scenario, non si parla tipicamente di "costanti di tempo" nel senso stretto come nei sistemi del primo ordine con poli reali. Tuttavia, il termine $\tau = \frac{1}{\sigma} = \frac{1}{\zeta \omega_n}$ è spesso chiamato **costante di tempo dell'inviluppo esponenziale** perché determina la velocità con cui l'ampiezza delle oscillazioni decade.
    * Il sistema risponde in modo oscillatorio con un'ampiezza che decade esponenzialmente.

**In Sintesi:**

* Se i poli sono reali e distinti ($ \zeta > 1 $), hai due costanti di tempo, l'inverso del valore assoluto di ciascun polo.
* Se i poli sono reali e coincidenti ($ \zeta = 1 $), hai una singola costante di tempo, l'inverso del valore assoluto del polo ripetuto.
* Se i poli sono complessi coniugati ($ 0 \le \zeta < 1 $), non hai costanti di tempo nel senso tradizionale, ma il reciproco della parte reale dei poli ($ \frac{1}{\zeta \omega_n} $) indica la velocità di decadimento dell'inviluppo delle oscillazioni.

Per trovare le costanti di tempo, devi prima identificare i poli della tua funzione di trasferimento del secondo ordine e poi applicare le relazioni appropriate a seconda della natura di questi poli.




