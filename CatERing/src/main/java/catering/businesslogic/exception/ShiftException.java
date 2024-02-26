package catering.businesslogic.exception;

public class ShiftException extends DomainException {
    private final String message;

    public ShiftException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
}
