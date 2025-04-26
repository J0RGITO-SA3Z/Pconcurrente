package Common;

import java.util.List;

public class MensajeListaUsuarios extends Mensaje {
    private List<String> nombresEquipos;

    public MensajeListaUsuarios(List<String> nombresEquipos) {    // HAY QUE CAMBIAR COSILLAS
        super("LISTA_USUARIOS");
        this.nombresEquipos = nombresEquipos;
    }

    public List<String> getNombresEquipos() {
        return nombresEquipos;
    }
}