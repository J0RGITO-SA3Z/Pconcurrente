import LocksI.Locks;

class HiloIncrementador extends Thread {
    private Entero contador;
    private final Locks lock;
    private int id;
    public HiloIncrementador(Entero contador, Locks l, int ide) {
        this.contador = contador;
        this.lock = l;
        this.id = ide;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10000; i++){

            this.lock.takeLock(id);
            contador.num = contador.num + 1;
            this.lock.releaseLock(id);
        }
    }
}