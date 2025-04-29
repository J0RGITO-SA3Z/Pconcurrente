package Common;

public class MensajeSolicitudDescarga extends Mensaje{
    private String nombre;
    private String nombreArchivo;
    public MensajeSolicitudDescarga(String usuario, String nombreArchivo) {
        super(TipoMensaje.SOLICITUD_DESCARGA);
        this.nombreArchivo = nombreArchivo;
        this.nombre = usuario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getNombre() {
        return nombre;
    }

}
