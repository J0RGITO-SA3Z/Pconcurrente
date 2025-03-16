package ThreadType;

import Almacen.Almacen;
import Almacen.Producto;

import java.util.Random;

public class Escritor extends Thread {
    private Almacen buffer_almacen;
    private int pid;
    private int P;
    private int cap;

    public Escritor(int id, Almacen buffer, int p ,int c) {
        buffer_almacen = buffer;
        pid = id;
        P = p;
        cap = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < P; i++) {
            Producto p = new Producto(new Random().nextInt(1000));
            Random rand = new Random();
            buffer_almacen.almacenar(p,rand.nextInt(cap));
            try {
                sleep(new Random().nextInt(1000) + 800); // Simula producción
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Productor ha salido  ");
    }
}
