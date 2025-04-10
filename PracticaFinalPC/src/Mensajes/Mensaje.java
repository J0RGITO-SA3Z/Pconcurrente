package Mensajes;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
    private final int tipo;

    public Mensaje(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
}

