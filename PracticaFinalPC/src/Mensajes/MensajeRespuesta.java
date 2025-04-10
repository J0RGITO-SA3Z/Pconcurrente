package Mensajes;

import Mensajes.Mensaje;

public class MensajeRespuesta extends Mensaje {
    private String contenido;

    public MensajeRespuesta(String contenido) {
        super(2); // Tipo 2 = Respuesta
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }
}
