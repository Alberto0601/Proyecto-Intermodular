package servicios;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServicioLog {

    private static final String ARCHIVO_DE_LOG = "logins.log";
    private static final DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void registrar(String nivel, String mensaje) {
        String fechaHora = LocalDateTime.now().format(formateador);
        String lineaLog = String.format("[%s] [%s] %s", fechaHora, nivel, mensaje);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_DE_LOG, true))) {
            bw.write(lineaLog);
            bw.newLine(); // Añade el salto de línea automático
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }

    public static void leerLogs() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_DE_LOG))) {
            String linea;
            System.out.println("--- CONTENIDO DEL ARCHIVO DE LOGS ---");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de log: " + e.getMessage());
        }
    }
}
