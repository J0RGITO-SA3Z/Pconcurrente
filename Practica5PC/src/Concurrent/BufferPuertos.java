package Concurrent;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class BufferPuertos implements Buffer {
    private int cap = 0;
    private int used = 0;
    private int pr = -1;
    private int con = -1;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex_a = new Semaphore(1);
    private Semaphore mutex_e = new Semaphore(1);
    private int[] contenedor;

    public BufferPuertos(int capacity){
        this.empty = new Semaphore(capacity);
        this.full = new Semaphore(0);
        this.contenedor = new int[capacity];
        cap = capacity;
        Arrays.fill(contenedor,-1); //vacío -> -1
    }
    @Override
    public void almacenar(int producto) {
        try {
            //ESPERAR A SER LLAMADO
            this.empty.acquire();
            this.mutex_a.acquire();

            pr = (pr + 1) % cap;
            contenedor[pr] = producto;
            used++;

            System.out.println("ALMACENADO:  " + producto + " | Espacios usados: " + used +"/" + cap);

            this.mutex_a.release();
            this.full.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int extraer() {
        int ext;
        try {
            //ESPERAR A SER LLAMADO
            this.full.acquire();
            this.mutex_e.acquire();

            con = (con + 1) % cap;
            ext = contenedor[con];
            contenedor[con] = -1;
            used--;

            System.out.println("EXTRAÍDO:  " + ext + " | Espacios usados: " + used + "/" + cap);

            this.mutex_e.release();
            this.empty.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ext;
    }
}
