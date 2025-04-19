package Common;

public class MensajeConexion extends Mensaje {
    private String nombreEquipo;
    private int puntos;

    public MensajeConexion(String nombreEquipo, int puntos) {
        super("CONEXION");
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
