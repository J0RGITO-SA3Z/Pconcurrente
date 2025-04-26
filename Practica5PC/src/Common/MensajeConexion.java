package Common;

public class MensajeConexion extends Mensaje {
    private String nombre;

    public MensajeConexion(String nombre) {
        super("CONEXION");
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

}
