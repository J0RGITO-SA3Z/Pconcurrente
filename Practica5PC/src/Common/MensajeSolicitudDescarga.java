package Common;

public class MensajeSolicitudDescarga extends Mensaje{
    private String nombre;
    private String propietario;
    private String nombreArchivo;
    public MensajeSolicitudDescarga(String usuario, String nombreArchivo,String propietario) {
        super(TipoMensaje.SOLICITUD_DESCARGA);
        this.nombreArchivo = nombreArchivo;
        this.nombre = usuario;
        this.propietario = propietario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPropietario() {
        return propietario;
    }
}
