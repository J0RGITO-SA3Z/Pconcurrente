package Common;

public class MensajePreparadorSCReceptor extends Mensaje {
    private String nombreEquipoEmisor;
    private String nombreEquipoReceptor;

    public MensajePreparadorSCReceptor(String nombreEquipoEmisor, String nombreEquipoReceptor) {
        super("PREPARADOR_SERVIDOR_CLIENTE_RECEPTOR");
        this.nombreEquipoEmisor = nombreEquipoEmisor;
        this.nombreEquipoReceptor = nombreEquipoReceptor;
    }

    public String getNombreEquipoEmisor() {
        return nombreEquipoEmisor;
    }

    public String getNombreEquipoReceptor() {
        return nombreEquipoReceptor;
    }
}
