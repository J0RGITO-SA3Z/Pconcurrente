package ThreadType;

import Almacen.Almacen;
import Almacen.Producto;

public class Consumidor extends Thread {
    private Almacen buffer_almacen;
    private int pid;
    private int C;

    public Consumidor(int id, Almacen buffer, int c) {
        buffer_almacen = buffer;
        pid = id;
        C = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < C; i++) {
            Producto p = buffer_almacen.extraer(0);
            try {
                Thread.sleep(2000); // Simula consumición
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Consumidor ha salido  ");
    }
}
