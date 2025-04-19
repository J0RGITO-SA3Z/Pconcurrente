package Client;

import java.io.Serializable;

class SolicitudPartida implements Serializable {
    private String nombreEquipo;
    private int puntosMinimos;

    public SolicitudPartida(String nombreEquipo, int puntosMinimos) {
        this.nombreEquipo = nombreEquipo;
        this.puntosMinimos = puntosMinimos;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public int getPuntosMinimos() {
        return puntosMinimos;
    }
}
