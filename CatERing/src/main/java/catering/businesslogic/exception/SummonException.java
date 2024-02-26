package catering.businesslogic.exception;

public class SummonException extends DomainException {
    private final String message;

    public SummonException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
}
