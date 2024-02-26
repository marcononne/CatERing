package catering.businesslogic.exception;

public class TaskException extends DomainException {
    private final String message;

    public TaskException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
}
