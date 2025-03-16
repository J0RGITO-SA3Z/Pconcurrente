package LocksI;

// Definición de la interfaz Procesos
public interface Locks {

    /**
     * Método para adquirir un lock.
     * Este método debe implementarse para asegurar que un recurso sea bloqueado antes de ser utilizado.
     */
    void takeLock(int id);

    /**
     * Método para liberar un lock.
     * Este método debe implementarse para liberar un recurso previamente bloqueado.
     */
    void releaseLock(int id);
}
