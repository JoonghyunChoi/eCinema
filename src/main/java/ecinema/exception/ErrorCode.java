package ecinema.domain;

public enum ErrorCode {

    BAD_REQUEST("Request is invalid!"),
    ACCESS_DENIED( "Access is denied!"),
    UNAUTHORIZED( "Authorization is required!"),
    METHOD_NOT_ALLOWED( "HTTP Method is not supported!"),
    INTERNAL_SERVER_ERROR("Internal server error!");


    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
