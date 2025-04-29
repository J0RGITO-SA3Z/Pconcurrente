package Common;

public class MensajePreparadorSCReceptor extends Mensaje {

    public MensajePreparadorSCReceptor(String nombreEquipoEmisor, String nombreEquipoReceptor) {
        super(TipoMensaje.PREPARADOR_SERVIDOR_CLIENTE_RECEPTOR);
    }

}
