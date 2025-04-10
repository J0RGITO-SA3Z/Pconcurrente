package LocksI;

/**
 * Implementación de un Bakery Lock para garantizar exclusión mutua en un sistema multihilo.
 *
 * Características:
 * - Justicia: Garantiza un acceso FIFO (First In, First Out), asegurando equidad entre los hilos.
 * - Eficiencia: Evita inanición y asegura que cada hilo eventualmente entre a la sección crítica.
 * - Escalabilidad: Funciona bien en sistemas con múltiples hilos y es adecuado para arquitecturas con memoria compartida.
 *
 * Cada hilo elige un número y espera hasta que tenga el número más bajo entre los hilos en espera, similar a un sistema de tickets en una panadería.
 */

public class BakeryLock implements Locks {
    private int n; // Número de procesos
    private volatile boolean[] eligiendo; // Indica si un proceso está eligiendo su número
    private volatile int[] numero; // Números asignados a cada proceso

    public BakeryLock(int n) {
        this.n = n;
        eligiendo = new boolean[n];
        numero = new int[n];
    }

    @Override
    public void takeLock(int id) {
        eligiendo[id] = true; eligiendo = eligiendo;// Indicar que está eligiendo un número
        numero[id] = 1 + max(numero); numero = numero;// Asigna el número más alto disponible
        eligiendo[id] = false; eligiendo = eligiendo;// Terminó de elegir su número

        // Esperar a que todos los procesos con menor número entren primero
        for (int j = 0; j < n; j++) {
            if (j == id) continue;
            while (eligiendo[j]) {Thread.yield();} // Espera a que el otro termine de elegir

            // Espera a que los procesos con menor número pasen primero
            while (numero[j] != 0 && (numero[j] < numero[id] || (numero[j] == numero[id] && j < id))) {Thread.yield();}
        }
    }

    @Override
    public void releaseLock(int id) {
        numero[id] = 0; numero = numero;// Libera su turno
    }

    private int max(int[] array) {
        int max = 0;
        for (int num : array) {
            if (num > max) max = num;
        }
        return max;
    }
}
