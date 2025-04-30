package View;

import Common.ConcurrentConsole;

import java.net.InetAddress;

public class ServidorVista {

    private final ConcurrentConsole ccout;

    public ServidorVista(ConcurrentConsole console) {
        this.ccout = console;
    }

    public void servidorIniciado() {ccout.print(ServidorMensajesConsola.SERVIDOR_INICIADO);}
    public void mensajeRecibido(String tipo) {ccout.print(ServidorMensajesConsola.MENSAJE_RECIBIDO + tipo);}

    public void equipoConectado(String nombre) {
        ccout.print(ServidorMensajesConsola.EQUIPO_CONECTADO + nombre);
    }

    public void listaInformacionSolicitada() {
        ccout.print(ServidorMensajesConsola.LISTA_INFORMACION_SOLICITADA);
    }

    public void usuario_preparado(String nombreEquipo) {ccout.print(ServidorMensajesConsola.EQUIPO_PREPARADO + nombreEquipo);}

    public void tipoMensajeDesconocido() {
        ccout.print(ServidorMensajesConsola.TIPO_MENSAJE_DESCONOCIDO);
    }

    public void nueva_conexion_cliente(InetAddress inetAddress) {ccout.print(ServidorMensajesConsola.NUEVA_CONEXION_CLIENTE + inetAddress);}

    public void descarga_archivo_solicitada(String nombrearchivo, String usuario) {ccout.print(ServidorMensajesConsola.DESCARGA_SOLICITADA + nombrearchivo + ServidorMensajesConsola.SOLICITADOR_DESCARGA + usuario);}

    public void cierre_conexion(String nombre) {ccout.print(ServidorMensajesConsola.CIERRE_SESION + nombre);}

    public void error_cerrar_recursos(String error){ ccout.print(ServidorMensajesConsola.ERROR_CERRAR_RECURSOS + error);}

    public void cliente_desconectado_abruptamente() { ccout.print(ServidorMensajesConsola.CLIENTE_DESCONECTADO_ABRUPTAMENTE);}

    public void error_descarga() {
    }
}

