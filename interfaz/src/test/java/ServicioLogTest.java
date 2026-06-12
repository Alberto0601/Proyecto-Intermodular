import org.junit.jupiter.api.Test;
import servicios.ServicioLog;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicioLogTest {
    @Test
    void debeEscribirLogEnArchivo() throws Exception {

        Path archivo = Path.of("logs.log");

        ServicioLog.registrar("INFO", "Usuario autenticado");

        assertTrue(Files.exists(archivo));

        List<String> lineas = Files.readAllLines(archivo);

        assertTrue(lineas.get(lineas.size()-1).contains("INFO"));
        assertTrue(lineas.get(lineas.size()-1).contains("Usuario autenticado"));
    }
    @Test
    void debeEscribirLogEnArchivo2() throws Exception {

        Path archivo = Path.of("logs.log");

        ServicioLog.registrar("INFO", "Usuario actualizado");

        assertTrue(Files.exists(archivo));

        List<String> lineas = Files.readAllLines(archivo);

        assertTrue(lineas.get(lineas.size()-1).contains("INFO"));
        assertTrue(lineas.get(lineas.size()-1).contains("Usuario actualizado"));
    }
    @Test
    void debeEscribirLogEnArchivo3() throws Exception {

        Path archivo = Path.of("logs.log");

        ServicioLog.registrar("ALERTA MI PANA", "Usuario borrado");

        assertTrue(Files.exists(archivo));

        List<String> lineas = Files.readAllLines(archivo);

        assertTrue(lineas.get(lineas.size()-1).contains("ALERTA MI PANA"));
        assertTrue(lineas.get(lineas.size()-1).contains("Usuario borrado"));
    }
    @Test
    void debeEscribirLogEnArchivo4() throws Exception {

        Path archivo = Path.of("logs.log");

        ServicioLog.registrar("ERROR", "Usuario no registrado");

        assertTrue(Files.exists(archivo));

        List<String> lineas = Files.readAllLines(archivo);

        assertTrue(lineas.get(lineas.size()-1).contains("ERROR"));
        assertTrue(lineas.get(lineas.size()-1).contains("Usuario no registrado"));
    }
    @Test
    void debeEscribirLogEnArchivo5() throws Exception {

        Path archivo = Path.of("logs.log");

        ServicioLog.registrar("WARNING", "Tas cagao jefe");

        assertTrue(Files.exists(archivo));

        List<String> lineas = Files.readAllLines(archivo);

        assertTrue(lineas.get(lineas.size()-1).contains("WARNING"));
        assertTrue(lineas.get(lineas.size()-1).contains("Tas cagao jefe"));
    }
}
