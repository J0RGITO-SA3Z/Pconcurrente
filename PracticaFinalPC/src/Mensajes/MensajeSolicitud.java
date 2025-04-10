package Mensajes;

import Mensajes.Mensaje;

public class MensajeSolicitud extends Mensaje {
    private String nombreFichero;

    public MensajeSolicitud(String nombreFichero) {
        super(1); // Tipo 1 = Solicitud
        this.nombreFichero = nombreFichero;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }
}
