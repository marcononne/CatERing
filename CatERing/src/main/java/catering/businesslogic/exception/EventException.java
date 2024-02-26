package catering.businesslogic.exception;

public class EventException extends DomainException {
    private final String message;

    public EventException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
