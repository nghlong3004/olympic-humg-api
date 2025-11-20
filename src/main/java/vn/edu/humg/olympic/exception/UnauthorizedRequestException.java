package vn.edu.humg.olympic.exception;

public class UnauthorizedRequestException extends RuntimeException {
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
