package Common;

public class MensajeAsignarNuevoPuerto extends Mensaje {
    private int puerto;
    public MensajeAsignarNuevoPuerto(int puerto) {
        super(TipoMensaje.ASIGNAR_NUEVO_PUERTO);
        this.puerto = puerto;
    }
    public int getPuerto() {
        return puerto;
    }
}
