package vn.edu.humg.olympic.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 4xx
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Not Found", "NotFoundError"),
    EMAIL_ALREADY(HttpStatus.CONFLICT.value(), "Email already exists.", "EmailAlready"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "Email or password is incorrect.", "InvalidCredentials"),

    // 5xx
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                   "The server encountered an internal error or misconfiguration and was unable to complete your request.");

    private final int status;
    private final String code;
    private final String message;

    ErrorResponse toErrorResponse() {
        return new ErrorResponse(message, status, code);
    }
}
