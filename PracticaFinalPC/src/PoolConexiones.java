import java.util.concurrent.Semaphore;

public class PoolConexiones {
    private final Semaphore semaforo = new Semaphore(10); // Máximo 10 conexiones

    public void adquirir() throws InterruptedException {
        semaforo.acquire();
    }

    public void liberar() {
        semaforo.release();
    }
}