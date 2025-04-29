package View;

import Common.ConcurrentConsole;

public class ClienteVista {

    private final ConcurrentConsole ccout;

    public ClienteVista(ConcurrentConsole console){
        this.ccout = console;
    }

    public void mostrarMenu() {
        ccout.print(ClienteMensajesConsola.MENU_PRINCIPAL);
    }

    public void conexion_cerrada(){
        ccout.print(ClienteMensajesConsola.SESION_CERRADA);
    }

    public void opcion_invalida(){
        ccout.print(ClienteMensajesConsola.OPCION_INVALIDA);
    }

    public void solicitud_conexion_enviada(){
        ccout.print(ClienteMensajesConsola.CONEXION_ENVIADA);
    }

    public void pedir_nombre_usuario(){
        ccout.print(ClienteMensajesConsola.PEDIR_NOMBRE);
    }

    public void error_puerto_servidor(String error){ccout.print(ClienteMensajesConsola.ERROR_CONEXION + error);}

    public void error_cerrar_recursos(String error){ ccout.print(ClienteMensajesConsola.ERROR_CERRAR_RECURSOS + error);}

    public void mensaje_desconocido(){
        ccout.print(ClienteMensajesConsola.MENSAJE_DESCONOCIDO);
    }

    public void equipo_preparado(String nombreEquipo){
        ccout.print(ClienteMensajesConsola.EQUIPO_PREPARADO + nombreEquipo);
    }

    public void lista_usuarios_solicitada(){
        ccout.print(ClienteMensajesConsola.LISTA_USUARIOS_SOLICITADA);
    }

    public void conexion_exitosa(){
        ccout.print(ClienteMensajesConsola.CONEXION_EXITOSA);
    }

    public void descarga_archivo_solicitada(String archivo) { ccout.print(ClienteMensajesConsola.DESCARGA_SOLICITADA + archivo);}
}
