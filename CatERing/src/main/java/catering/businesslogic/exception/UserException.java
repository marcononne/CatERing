package catering.businesslogic.exception;

public class UserException extends DomainException {
    private final String message;

    public UserException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
