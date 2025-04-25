package Common;

public class MensajeConfConexion extends Mensaje {
    private String nombreEquipo;
    private int puntos;

    public MensajeConfConexion(String nombreEquipo, int puntos) {
        super("CONFIRMACION_CONEXION");
        this.nombreEquipo = nombreEquipo;
        this.puntos = puntos;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public int getPuntos() {
        return puntos;
    }

}
