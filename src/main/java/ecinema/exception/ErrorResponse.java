package ecinema.domain;


import lombok.Data;


@Data
public class ErrorResponse {

    private String message;

    public ErrorResponse(ErrorCode errorCode) {

        this.message = errorCode.getMessage();
    }
}
