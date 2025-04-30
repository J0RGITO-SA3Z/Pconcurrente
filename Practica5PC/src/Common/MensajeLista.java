package Common;

import java.util.List;
import java.util.Map;

public class MensajeLista extends Mensaje {
    private Map<String, List<String>> informacion;

    public MensajeLista(Map<String, List<String>> informacion) {
        super(TipoMensaje.LISTA_INFORMACION);
        this.informacion = informacion;
    }

    public  Map<String, List<String>> getinformacion() {
        return informacion;
    }
}