import LocksI.*;

import java.util.concurrent.Semaphore;


class Entero {
    public volatile int num; // Variable compartida sin sincronización

    public Entero() {
        num = 0;
    }

    public int getNum() {
        return num;
    }

    public void set(int a){
        num = a;
    }
}


public class Main {

    public static int m = 20; // Número de hilos que decrementan
    public static int k = 20; // Número de hilos que incrementan
    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {

        long startTime;
        long endTime;
        long duration;

        //Parte 1 ---------------------------------------------------------------
        LockBinRompeEmpate lrb = new LockBinRompeEmpate();
        Entero contador_1 = testparte1(lrb);

        System.out.println("PARTE 1 : \nLock Rompe-Empate Binario \nValor final del contador: " + contador_1.getNum());

        BakeryLockBin blb = new BakeryLockBin();
        contador_1 = testparte1(blb);

        System.out.println("Bakery Lock Binario \nValor final del contador: " + contador_1.getNum());

        contador_1 = testparte1(new LockVacio());

        System.out.println("Asincrono  \nValor final del contador: " + contador_1.getNum() + '\n');


        //Parte 2 ---------------------------------------------------------------

        LockRompeEmpate lr = new LockRompeEmpate(m+k);
        startTime = System.currentTimeMillis();
        Entero contador = testparte2(m,k,lr);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("PARTE 2 : \nLock Rompe-Empate con " + (m+k) +" hilos\nValor final del contador: " + contador.getNum()+"  time: " + duration);

        BakeryLock bl = new BakeryLock(m+k);
        startTime = System.currentTimeMillis();
        contador = testparte2(m,k,bl);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Bakery Lock con " + (m+k) +" hilos\nValor final del contador: " + contador.getNum()+"  time: " + duration);

        LockTicket lt = new LockTicket();
        startTime = System.currentTimeMillis();
        contador = testparte2(m,k,lt);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Lock ticket con " + (m+k) +" hilos\nValor final del contador: " + contador.getNum() +"  time: " + duration);

        startTime = System.currentTimeMillis();
        contador = testparte2MODP3(m,k);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Clase Semaphore con " + (m+k) +" hilos\nValor final del contador: " + contador.getNum() +"  time: " + duration);


        contador = testparte2(m,k,new LockVacio());
        System.out.println("Asincrono con " + (m+k) +" hilos\nValor final del contador: " + contador.getNum() + '\n');
    }



    public static Entero testparte1(Locks lk){
        Entero contador_1 = new Entero();
        Thread[] hilos_1 = new Thread[2];

        hilos_1[0] = new HiloIncrementador(contador_1,lk,0);
        hilos_1[1] = new HiloDecrementador(contador_1,lk,1);
        hilos_1[0].start();
        hilos_1[1].start();

        for (Thread hilo : hilos_1) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return contador_1;
    }


    public static Entero testparte2(int m, int k, Locks lk){
        Entero contador = new Entero();
        Thread[] hilos = new Thread[m + k];

        // Crear hilos decrementadores
        for (int i = 0; i < m; i++) {
            hilos[i] = new HiloDecrementador(contador,lk,i);
            hilos[i].start();
        }

        // Crear hilos incrementadores
        for (int i = 0; i < k; i++) {
            hilos[m + i] = new HiloIncrementador(contador,lk,m + i);
            hilos[m + i].start();
        }

        // Esperar a que terminen todos los hilos
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return contador;
    }

    public static Entero testparte2MODP3(int m, int k){
        Entero contador = new Entero();
        Thread[] hilos = new Thread[m + k];

        // Crear hilos decrementadores
        for (int i = 0; i < m; i++) {
            hilos[i] = new HiloDecrementadorSEM(contador,i,semaphore);
            hilos[i].start();
        }

        // Crear hilos incrementadores
        for (int i = 0; i < k; i++) {
            hilos[m + i] = new HiloIncrementadorSEM(contador,m + i,semaphore);
            hilos[m + i].start();
        }

        // Esperar a que terminen todos los hilos
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return contador;
    }
}