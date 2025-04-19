package Common;

import java.io.Serializable;

public class MensajeJuego implements Serializable {
    private String accion;
    private int valor;

    public MensajeJuego(String accion, int valor) {
        this.accion = accion;
        this.valor = valor;
    }

    // Getters y setters
}