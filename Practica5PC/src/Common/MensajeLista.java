package Common;

import java.util.List;
import java.util.Map;

public class MensajeLista extends Mensaje {
    private Map<Integer, String> usuarios;

    public MensajeLista(List<String> nombresEquipos,Map<Integer, String> usuarios) {    // HAY QUE CAMBIAR COSILLAS
        super(TipoMensaje.LISTA_USUARIOS);
        this.usuarios = usuarios;
    }

    public  Map<Integer, String> getNombresEquipos() {
        return usuarios;
    }
}