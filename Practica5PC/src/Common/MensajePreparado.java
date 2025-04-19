package Common;

public class MensajePreparado extends Mensaje {
    private String nombreEquipo;

    public MensajePreparado(String nombreEquipo) {
        super("PREPARADO");
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }
}
