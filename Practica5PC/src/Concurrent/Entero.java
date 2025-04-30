package Concurrent;

public class Entero {
    public volatile int num; // Variable compartida volatil

    public Entero() {
        num = 0;
    }

    public int getNum() {
        return num;
    }

    public void set(int a){
        num = a;
    }
    public void decrement(){num--;}
    public void increment(){num++;}
}
