package Common;

public class MensajePreparado extends Mensaje {
    private String nombre;

    public MensajePreparado(String nombreEquipo) {
        super(TipoMensaje.PREPARADO);
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
