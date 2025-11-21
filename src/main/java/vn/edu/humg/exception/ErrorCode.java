package vn.edu.humg.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // 4xx
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Not Found", "NotFoundError"),

    // 5xx
    INTERNAL_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "The server encountered an internal error or misconfiguration and was unable to complete your request."
    );

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    ErrorResponse toErrorResponse() {
        return new ErrorResponse(message, status, code);
    }
}
