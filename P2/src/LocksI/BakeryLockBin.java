package LocksI;

import java.util.Arrays;

public class BakeryLockBin implements Locks{

    private volatile boolean[] eligiendo; // Indica si un proceso está eligiendo su número
    private volatile int[] numero; // Números asignados a cada proceso

    public BakeryLockBin(){
        eligiendo = new boolean[2];
        numero = new int[2];
        numero[0] = 0;
        numero[1] = 0;
        eligiendo[0] = false;
        eligiendo[1] = false;
    }

    @Override
    public void takeLock(int id) {
        int otro = 1 - id; // El otro proceso (si id=0, otro=1 y viceversa)

        eligiendo[id] = true; eligiendo = eligiendo;// Indicar que el proceso está eligiendo un número
        numero[id] = 1 + Math.max(numero[0], numero[1]); numero = numero;// Asigna el número más alto disponible
        eligiendo[id] = false;  eligiendo = eligiendo;// Ya eligió su número

        // Espera a que el otro proceso termine de elegir su número
        while (eligiendo[otro]) {}

        // Espera a que el otro proceso con menor número entre primero
        while (numero[otro] != 0 &&
                (numero[otro] < numero[id] || (numero[otro] == numero[id] && otro < id))) {}

        // En este punto, el proceso puede entrar a la sección crítica

    }

    @Override
    public void releaseLock(int id) {
        numero[id] = 0; numero = numero;
    }

}
