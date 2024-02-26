package catering.businesslogic.exception;

public class UseCaseLogicException extends Exception {
    private final String message;

    public UseCaseLogicException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
