package catering.businesslogic.exception;

abstract class DomainException extends Exception {
    private final String message;

    public DomainException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
