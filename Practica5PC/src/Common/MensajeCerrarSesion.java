package Common;

public class MensajeCerrarSesion extends Mensaje{
    private String nombre;
    public MensajeCerrarSesion(String nombre) {
        super(TipoMensaje.CERRAR_SESION);
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
