package ThreadType;

import Almacen.Almacen;
import Almacen.Producto;

import java.util.Random;

public class Lector extends Thread {
    private Almacen buffer_almacen;
    private int pid;
    private int C;
    private int cap;

    public Lector(int id, Almacen buffer, int c,int capa) {
        buffer_almacen = buffer;
        pid = id;
        C = c;
        cap = capa;
    }

    @Override
    public void run() {
        for (int i = 0; i < C; i++) {
            Random rand = new Random();
            Producto p = buffer_almacen.extraer(rand.nextInt(cap));
            try {
                Thread.sleep(rand.nextInt(1000) + 800); // Simula consumición
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Consumidor ha salido  ");
    }
}
