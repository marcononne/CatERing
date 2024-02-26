package catering.businesslogic.exception;

public class AvailabilityException extends DomainException {
    private final String message;

    public AvailabilityException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
    return message;
  }
}
