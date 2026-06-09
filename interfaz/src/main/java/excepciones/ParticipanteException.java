package excepciones;

public class ParticipanteException extends Exception {

    public ParticipanteException() {
        super("Error, participante duplicado");
    }

    public ParticipanteException(String mensaje) {
        super(mensaje);
    }

    public ParticipanteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
