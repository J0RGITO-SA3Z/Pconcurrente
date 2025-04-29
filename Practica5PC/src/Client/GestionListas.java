package Client;

import java.io.*;
import java.util.Scanner;

public class GestionListas {
    private String rutaUsuario;

    public GestionListas(String nombreUsuario) {
        this.rutaUsuario = "usuarios/" + nombreUsuario + "/";
        File directorio = new File(rutaUsuario);
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crear el directorio si no existe
        }
    }

    public void gestionarListas() {
        System.out.println("GESTIONANDO LISTAS...");
        File directorio = new File(rutaUsuario);
        File[] archivos = directorio.listFiles((dir, name) -> name.endsWith(".txt"));

        if (archivos == null || archivos.length == 0) {
            System.out.println("No tienes listas disponibles.");
        } else {
            // Mostrar las listas disponibles
            System.out.println("Tus listas disponibles:");
            for (int i = 0; i < archivos.length; i++) {
                System.out.println((i + 1) + ". " + archivos[i].getName());
            }
        }

        // Menú principal
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Qué deseas hacer? (1: Ver lista, 2: Crear lista, 3: Modificar lista, 4: Eliminar lista, 0: Cancelar): ");
        int accion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        switch (accion) {
            case 0:
                System.out.println("Acción cancelada.");
                break;
            case 1: // Ver contenido de la lista
                verContenidoLista(archivos);
                break;
            case 2: // Crear una nueva lista
                crearNuevaLista(scanner);
                break;
            case 3: // Modificar una lista existente
                modificarListaExistente(archivos, scanner);
                break;
            case 4: // Eliminar una lista
                eliminarLista(archivos, scanner);
                break;
            default:
                System.out.println("Acción inválida.");
        }
    }

    private void verContenidoLista(File[] archivos) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Selecciona el número de la lista que deseas ver: ");
        int opcion = scanner.nextInt();

        if (opcion > 0 && opcion <= archivos.length) {
            File archivoSeleccionado = archivos[opcion - 1];
            System.out.println("Mostrando contenido de la lista: " + archivoSeleccionado.getName());
            try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void crearNuevaLista(Scanner scanner) {
        System.out.print("Introduce el nombre de la nueva lista (sin espacios ni caracteres especiales): ");
        String nombreLista = scanner.nextLine().trim();
        if (nombreLista.isEmpty()) {
            System.out.println("El nombre de la lista no puede estar vacío.");
            return;
        }

        File nuevaLista = new File(rutaUsuario + nombreLista + ".txt");
        if (nuevaLista.exists()) {
            System.out.println("Ya existe una lista con ese nombre.");
            return;
        }

        try {
            if (nuevaLista.createNewFile()) {
                System.out.println("Lista creada exitosamente: " + nuevaLista.getName());
            } else {
                System.out.println("Error al crear la lista.");
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    private void modificarListaExistente(File[] archivos, Scanner scanner) {
        System.out.print("Selecciona el número de la lista que deseas modificar: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        if (opcion > 0 && opcion <= archivos.length) {
            File archivoSeleccionado = archivos[opcion - 1];
            System.out.println("Modificando la lista: " + archivoSeleccionado.getName());

            // Leer el contenido actual de la lista
            StringBuilder contenido = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
                return;
            }

            // Mostrar el contenido actual y permitir editarlo
            System.out.println("Contenido actual de la lista:");
            System.out.println(contenido.toString());
            System.out.println("Introduce el nuevo contenido de la lista (escribe 'FIN' en una línea nueva para terminar):");

            // Leer el nuevo contenido
            StringBuilder nuevoContenido = new StringBuilder();
            String linea;
            while (!(linea = scanner.nextLine()).equalsIgnoreCase("FIN")) {
                nuevoContenido.append(linea).append("\n");
            }

            // Sobrescribir el archivo con el nuevo contenido
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSeleccionado))) {
                bw.write(nuevoContenido.toString());
                System.out.println("Lista modificada exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al escribir en el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void eliminarLista(File[] archivos, Scanner scanner) {
        System.out.print("Selecciona el número de la lista que deseas eliminar: ");
        int opcion = scanner.nextInt();

        if (opcion > 0 && opcion <= archivos.length) {
            File archivoSeleccionado = archivos[opcion - 1];
            if (archivoSeleccionado.delete()) {
                System.out.println("Lista eliminada: " + archivoSeleccionado.getName());
            } else {
                System.out.println("Error al eliminar la lista.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    public File obtenerArchivo(String nombreLista) {
        return new File(rutaUsuario + nombreLista);
    }
}