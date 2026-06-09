package excepciones;

public class ConcursoException extends Exception {

    public ConcursoException() {
        super("Error, el concurso ya existe");
    }

    public ConcursoException(String mensaje) {
        super(mensaje);
    }

    public ConcursoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
