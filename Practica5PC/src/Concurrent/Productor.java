package Concurrent;

import java.io.IOException;

public class Productor extends Thread {
    private Buffer buffer_puertos;
    private int pid;
    private boolean ejecutando;
    private int puertoActual = 49152;
    private final static int MIN_PORT = 49152;
    private final static int MAX_PORT = 65535;

    public Productor(int id, Buffer buffer) {
        buffer_puertos = buffer;
        pid = id;
        ejecutando = true;
    }

    @Override
    public void run() {
        while(ejecutando) {
            int s = puertoActual;
            puertoActual = MIN_PORT + ( (puertoActual - MIN_PORT + 1) % (MAX_PORT - MIN_PORT + 1) ); //entre 49152 y 65535
            buffer_puertos.almacenar(s);
        }
        System.out.println("El productor ha salido.");
    }




}
