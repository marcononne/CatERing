package catering.businesslogic.exception;

public class StaffMemberException extends DomainException {
    private final String message;

    public StaffMemberException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
}
