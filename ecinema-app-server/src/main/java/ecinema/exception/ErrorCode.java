package ecinema.exception;

public enum ErrorCode {
    INVALID_REQUEST_VALUE("Request value is not valid"),
    INVALID_REQUEST_TYPE("Request type is not valid"),
    ACCESS_DENIED( "Access is denied"),
    UNAUTHORIZED( "Authorization is required"),
    METHOD_NOT_ALLOWED( "Unsupported HTTP method"),
    INVALID_PRICE("Price is not valid"),
    ENTITY_NOT_FOUND("Entity not found"),
    INTERNAL_SERVER_ERROR("Internal server error");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}