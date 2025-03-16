package ThreadType;

import Almacen.Almacen;
import Almacen.Producto;
import java.util.Random;

public class Productor extends Thread {
    private Almacen buffer_almacen;
    private int pid;
    private int P;

    public Productor(int id, Almacen buffer, int p) {
        buffer_almacen = buffer;
        pid = id;
        P = p;
    }

    @Override
    public void run() {
        for (int i = 0; i < P; i++) {
            Producto p = new Producto(new Random().nextInt(1000));
            buffer_almacen.almacenar(p,0);
            try {
                sleep(new Random().nextInt(1000)); // Simula producción
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Productor ha salido  ");
    }
}
