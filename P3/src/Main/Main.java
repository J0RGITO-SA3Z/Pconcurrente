package Main;

import Almacen.Almacen;
import Almacen.AlmacenArray;
import Almacen.AlmacenConcreto;
import ThreadType.Consumidor;
import ThreadType.Escritor;
import ThreadType.Lector;
import ThreadType.Productor;

public class Main {
    private final static int CAPACITY = 5;
    private final static int PRODUCERS = 10;
    private final static int PRODUCERS_ITERATIONS = 3;
    private final static int CONSUMERS = 10;
    private final static int CONSUMERS_ITERATIONS = 3;

    public static void main(String[] args) {
        lector_escritor_testigo();
        //productor_consumidor_sem();
    }

    public static void productor_consumidor_sem(){
        AlmacenArray almacen = new AlmacenArray(CAPACITY);
        prueba_almacen(almacen);
    }

    public static void lector_escritor_testigo(){
        Almacen almacen = new AlmacenConcreto(CAPACITY);
        Thread[] hilos = new Thread[PRODUCERS + CONSUMERS];
        // Crear hilos decrementadores
        for (int i = 0; i < PRODUCERS; i++) {
            hilos[i] = new Escritor(i,almacen,PRODUCERS_ITERATIONS,CAPACITY);
            hilos[i].start();
        }

        // Crear hilos incrementadores
        for (int i = 0; i < CONSUMERS; i++) {
            hilos[PRODUCERS + i] = new Lector(i,almacen,CONSUMERS_ITERATIONS,CAPACITY);
            hilos[PRODUCERS + i].start();
        }

        // Esperar a que terminen todos los hilos
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void prueba_almacen(Almacen almacen){
        Thread[] hilos = new Thread[PRODUCERS + CONSUMERS];
        // Crear hilos decrementadores
        for (int i = 0; i < PRODUCERS; i++) {
            hilos[i] = new Productor(i,almacen,PRODUCERS_ITERATIONS);
            hilos[i].start();
        }

        // Crear hilos incrementadores
        for (int i = 0; i < CONSUMERS; i++) {
            hilos[PRODUCERS + i] = new Consumidor(i,almacen,CONSUMERS_ITERATIONS);
            hilos[PRODUCERS + i].start();
        }

        // Esperar a que terminen todos los hilos
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
