package ecinema.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_VALUE);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        log.error("handleMethodArgumentTypeMismatchException", e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_TYPE);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        log.error("handleHttpRequestMethodNotSupportedException", e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {

        log.error("handleAccessDeniedException", e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ACCESS_DENIED);

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {

        log.error("handleBusinessException", e);
        final ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {

        log.error("handleException", e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
