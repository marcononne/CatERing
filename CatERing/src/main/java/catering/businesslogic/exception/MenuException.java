package catering.businesslogic.exception;

public class MenuException extends DomainException {
    private final String message;

    public MenuException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
}
