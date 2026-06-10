package servicios;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServicioLog {

    private static final String ARCHIVO_DE_LOG = "logs.log";
    private static final DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * metodo que me registra en mi archivo .log los inicios de sesión
     * @param nivel
     * @param mensaje
     */
    public static void registrar(String nivel, String mensaje) {
        String fechaHora = LocalDateTime.now().format(formateador);
        String lineaLog = String.format("[%s] [%s] %s", fechaHora, nivel, mensaje);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_DE_LOG, true))) {
            bw.write(lineaLog);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }
}
