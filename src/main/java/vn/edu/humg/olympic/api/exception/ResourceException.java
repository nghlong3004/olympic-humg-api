package vn.edu.humg.olympic.api.exception;

import lombok.Getter;

@Getter
public class ResourceException extends RuntimeException {

    private final ErrorCode errorCode;

    public ResourceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
