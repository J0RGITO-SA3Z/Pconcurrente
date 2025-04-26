package Concurrencia.LocksI;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementación de un Ticket Lock para garantizar exclusión mutua en un sistema multihilo.
 *
 * Características:
 * - Justicia: Garantiza un acceso FIFO (First In, First Out), evitando inanición.
 * - Eficiencia: Reduce la contención entre hilos, pero utiliza espera activa (busy waiting).
 * - Escalabilidad: Funciona bien en sistemas con múltiples hilos, pero puede ser ineficiente en sistemas de un solo núcleo.
 *
 * Cada hilo obtiene un número de ticket y espera hasta que sea su turno para ejecutar la sección crítica.
 */
public class LockTicket implements Locks {

    private AtomicInteger ticket = new AtomicInteger(0); // Número de ticket actual que se asignará al siguiente hilo
    private int turno = 0;  // Número de ticket del hilo que actualmente puede entrar en la sección crítica

    /**
     * Método para adquirir el lock (espera activa hasta que el hilo pueda entrar a la sección crítica).
     * @param id Identificador del hilo que solicita el lock (no se usa en esta implementación).
     */
    @Override
    public void takeLock(int id) {
        int miTicket = ticket.getAndIncrement(); // El hilo obtiene un ticket único (incremental)

        // Espera activa hasta que su ticket coincida con el turno actual
        while (miTicket != turno) {
            Thread.yield();
            // Espera sin hacer nada, lo que puede ser ineficiente en algunos sistemas
        }
    }

    /**
     * Método para liberar el lock, permitiendo que el siguiente hilo en la cola entre a la sección crítica.
     * @param id Identificador del hilo que libera el lock (no se usa en esta implementación).
     */
    @Override
    public void releaseLock(int id) {
        turno++; // Incrementa el turno para permitir que el siguiente hilo entre a la sección crítica
    }
}
