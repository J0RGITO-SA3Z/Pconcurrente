package Common;

public class MensajeConexion extends Mensaje {
    private String nombre;

    public MensajeConexion(String nombreEquipo) {
        super("CONEXION");
        this.nombre = nombreEquipo;
    }

    public String getNombre() { return nombre; }

}
