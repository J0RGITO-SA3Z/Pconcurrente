package LocksI;

import java.util.Arrays;

public class LockBinRompeEmpate implements Locks{

    private volatile int last;
    private volatile boolean[] espera;

    public LockBinRompeEmpate(){
        last = -1;
        this.espera = new boolean[2];
        Arrays.fill(espera,false);
    }

    @Override
    public void takeLock(int id) {
        int otro = 1 - id; // El otro proceso (si id=0 -> otro=1, si id=1 -> otro=0)

        espera[id] = true; // Este proceso quiere entrar
        last = id; // Indica que este proceso fue el último en pedir acceso

        // Espera mientras el otro proceso quiere entrar y tiene prioridad sobre este
        while (espera[otro] && last == id) {
            // Espera ocupada hasta que el otro proceso libere la sección crítica
        }
    }

    /*
    public void takeLock(int id) {
        last = id;
        espera[id] = true;

        for (int k = 0; k < 2; ++k) {
                if (k == id) {continue;}
                while ( espera[k] && last == id) {};
        }

    }

     */

    @Override
    public void releaseLock(int id) {
        espera[id] = false; espera = espera;
    }
}
