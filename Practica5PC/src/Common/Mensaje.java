package Common;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tipo; // Tipo de mensaje (por ejemplo, "CONEXION", "LISTA_USUARIOS", etc.)

    public Mensaje(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
