package Common;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;
    private TipoMensaje tipo; // Tipo de mensaje (por ejemplo, "CONEXION", "LISTA_USUARIOS", etc.)

    public Mensaje(TipoMensaje tipo) {
        this.tipo = tipo;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }
}
