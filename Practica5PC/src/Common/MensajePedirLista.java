package Common;

import java.io.Serializable;

public class MensajePedirLista extends Mensaje {
    private String accion;

    public MensajePedirLista(String accion, int valor) {
        super("SOLICITUD_LISTA");
        this.accion = accion;
    }

    public String getAccion() {
        return accion;
    }
}