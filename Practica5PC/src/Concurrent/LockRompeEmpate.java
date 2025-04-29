package Concurrent;

import java.util.Arrays;

/**
 * Implementación de un Lock Rompe Empate para garantizar exclusión mutua en un sistema multihilo.
 *
 *  Características:
 *  - Justicia: No garantiza un orden estricto de llegada, pero evita inanición y garantiza progreso.
 *  - Eficiencia: Es un algoritmo simple y liviano que usa solo variables booleanas y enteras.
 *  - Escalabilidad: Funciona bien para dos hilos, pero su extensión a más hilos no es trivial.
 *
 * Se basa en que cada hilo que espere a entra en la sección crítica debe pasar por varias fases
 */

public class LockRompeEmpate implements Locks {
    private int tamanyo;
    private volatile int[] last;
    private volatile int[] in;

    public LockRompeEmpate(int m) {
        last = new int[m] ;
        in =  new int[m] ;
        this.tamanyo = m;
        Arrays.fill(last,-1);
        Arrays.fill(in,0);
    }

    @Override
    public void takeLock(int id) {
        for (int j = 0 ; j < tamanyo; j++) { /* entry protocol */
            /* remember process i is in stage j and is last */
            last[j] = id; last = last; in[id] = j; in = in;
            for (int k = 0; k < tamanyo; k++) {
                if (k == id) {continue;}
                while (in[k] >= in[id] && last[j] == id) {Thread.yield();};
            }
        }
    }

    @Override
    public void releaseLock(int id) {
        in[id] = -1; in = in;
    }
}