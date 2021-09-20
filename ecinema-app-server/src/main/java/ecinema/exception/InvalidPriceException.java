package ecinema.exception;


public class InvalidPriceException extends BusinessException {

    public InvalidPriceException(String message) {
        super(message, ErrorCode.INVALID_PRICE);
    }

    public InvalidPriceException() {
        super(ErrorCode.INVALID_PRICE);
    }
}
