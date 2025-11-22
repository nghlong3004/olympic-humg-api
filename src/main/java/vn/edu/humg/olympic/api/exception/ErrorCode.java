package vn.edu.humg.olympic.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 4xx
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "NotFoundError", "Not Found"),
    EMAIL_ALREADY(HttpStatus.CONFLICT.value(), "EmailAlready", "Email already exists."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "InvalidCredentials", "Email or password is incorrect."),
    // 5xx
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "InternalError",
                   "The server encountered an internal error or misconfiguration and was unable to complete your request.");

    private final int status;
    private final String code;
    private final String message;

    ErrorResponse toErrorResponse() {
        return new ErrorResponse(message, status, code);
    }
}
