package excepciones;

public class ConcursoDuplicadoException extends Exception {

    public ConcursoDuplicadoException() {
        super("Error, el concurso ya existe");
    }

    public ConcursoDuplicadoException(String mensaje) {
        super(mensaje);
    }

    public ConcursoDuplicadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
