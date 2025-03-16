package Almacen;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class AlmacenConcreto implements Almacen {
    private int cap; // Capacidad del almacén
    private Producto[] contenedor; // Array para almacenar los productos
    private final Semaphore e = new Semaphore(1); // Exclusión mutua para acceso al almacén
    private final Semaphore r = new Semaphore(0); // Semáforo para retrasar lectores si hay escritores
    private final Semaphore w = new Semaphore(0); // Semáforo para retrasar escritores si hay lectores
    private int nr = 0, nw = 0, dr = 0, dw = 0; // Contadores de lectores, escritores y procesos retrasados

    public AlmacenConcreto(int capacity) {
        this.cap = capacity;
        this.contenedor = new Producto[capacity];
        Arrays.fill(contenedor, null); // Inicializa el almacén vacío
    }

    @Override
    public Producto extraer(int pos) {
        Producto ext = null;
        try {
            e.acquire(); // Adquirir exclusión mutua
            if (nw > 0) { // Si hay escritores activos, el lector se retrasa
                dr++;
                e.release();
                r.acquire();
            }
            nr++; // Incrementar el número de lectores
            if (dr > 0) { // Si hay lectores retrasados, liberar uno
                dr--;
                r.release();
            } else {
                e.release();
            }

            ext = contenedor[pos]; // Leer el producto en la posición indicada
            System.out.println("LEIDO: " + ext);
            extraer_release(); // Liberar el acceso
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return ext;
    }

    private void extraer_release() {
        try {
            e.acquire(); // Adquirir exclusión mutua
            nr--; // Decrementar número de lectores activos
            if (nr == 0 && dw > 0) { // Si no quedan lectores y hay escritores esperando, liberar uno
                dw--;
                w.release();
            } else {
                e.release();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void almacenar(Producto producto, int pos) {
        try {
            e.acquire(); // Adquirir exclusión mutua
            if (nr > 0 || nw > 0) { // Si hay lectores o escritores activos, el escritor se retrasa
                dw++;
                e.release();
                w.acquire();
            }
            nw++; // Incrementar número de escritores activos
            e.release();

            contenedor[pos] = producto; // Escribir el producto en la posición indicada
            System.out.println("ESCRITO: " + producto);
            almacenar_release(); // Liberar el acceso
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void almacenar_release() {
        try {
            e.acquire(); // Adquirir exclusión mutua
            nw--; // Decrementar número de escritores activos
            if (dr > 0) { // Si hay lectores esperando, liberar uno
                dr--;
                r.release();
            } else if (dw > 0) { // Si hay escritores esperando, liberar uno
                dw--;
                w.release();
            } else {
                e.release();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
