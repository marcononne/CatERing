package catering.businesslogic.exception;

public class ProcedureException extends DomainException {
    private final String message;

    public ProcedureException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
}
