package Common;

public class MensajeConfConexion extends Mensaje {
    private String nombreEquipo;

    public MensajeConfConexion(String nombreEquipo) {
        super("CONFIRMACION_CONEXION");
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

}
