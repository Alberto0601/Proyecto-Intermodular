package excepciones;

public class ConcursoException extends Exception {

    public ConcursoException() {
        super("Error, concurso no insertado");
    }

    public ConcursoException(String mensaje) {
        super(mensaje);
    }

    public ConcursoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
