package vn.edu.humg.exception;

public class SampleException extends RuntimeException {

    private final ErrorCode errorCode;

    public SampleException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
