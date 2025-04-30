package Common;

import java.net.ServerSocket;

public class MensajeConfConexion extends Mensaje {
    private String nombre;

    private int puerto;

    public MensajeConfConexion(String nombre,int puerto) {
        super(TipoMensaje.CONFIRMACION_CONEXION);
        this.nombre = nombre;
        this.puerto = puerto;
    }

    public String getNombreEquipo() {
        return nombre;
    }
    public int getPuerto() {
        return puerto;
    }

}
