package Common;

public class MensajeConfConexion extends Mensaje {
    private String nombre;

    public MensajeConfConexion(String nombreEquipo) {
        super("CONFIRMACION_CONEXION");
        this.nombre = nombreEquipo;
    }

    public String getNombreEquipo() {
        return nombre;
    }

}
