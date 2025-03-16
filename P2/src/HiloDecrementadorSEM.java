import LocksI.Locks;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
public class HiloDecrementadorSEM extends Thread {
    private Entero contador;
    private Semaphore semaphore = new Semaphore(1);
    private int id;
    public HiloDecrementadorSEM(Entero contador, int ide,Semaphore sem) {
        this.contador = contador;
        this.semaphore = sem;
        this.id = ide;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10000; i++){
            try {
                semaphore.acquire();
                contador.num = contador.num - 1;  // No sincronizado -> Condiciones de carrera
                semaphore.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}