package Common;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MensajeInformacionCiente extends Mensaje{

    private String nombre;
    private List<String> datos;
    public MensajeInformacionCiente(List<String> datos,String nombre) {
        super(TipoMensaje.INFORMACION_CLIENTE);
        this.nombre = nombre;
        this.datos = datos;
    }

    public List<String> getDatos() {
        return datos;
    }

    public String getNombre() {
        return nombre;
    }
}
