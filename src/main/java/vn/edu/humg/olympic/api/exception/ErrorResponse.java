package vn.edu.humg.olympic.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String message,
    int status,
    String code
) {
}
