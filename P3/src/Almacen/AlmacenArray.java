package Almacen;

import Almacen.Almacen;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class AlmacenArray implements Almacen {
    private int cap = 0;
    private int used = 0;
    private int pr = -1;
    private int con = -1;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex = new Semaphore(1);
    private Producto[] contenedor;

    public AlmacenArray(int capacity){
        this.empty = new Semaphore(capacity);
        this.full = new Semaphore(0);
        this.contenedor = new Producto[capacity];
        cap = capacity;
        Arrays.fill(contenedor,null);
    }
    @Override
    public void almacenar(Producto producto,int pos) {
        try {
            //ESPERAR A SER LLAMADO
            this.empty.acquire();
            this.mutex.acquire();

            pr = (pr + 1) % cap;
            contenedor[pr] = producto;
            used++;

            System.out.println("ALMACENADO:  " + producto + " | Espacios usados: " + used +"/" + cap);

            this.mutex.release();
            this.full.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Producto extraer(int pos) {
        Producto ext;
        try {
            //ESPERAR A SER LLAMADO
            this.full.acquire();
            this.mutex.acquire();

            con = (con + 1) % cap;
            ext = contenedor[con];
            contenedor[con] = null;
            used--;

            System.out.println("EXTRAÍDO:  " + ext + " | Espacios usados: " + used +"/" + cap);

            this.mutex.release();
            this.empty.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ext;
    }
}
