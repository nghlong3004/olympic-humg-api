package vn.edu.humg.olympic.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import vn.edu.humg.olympic.model.response.ErrorResponse;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(BadRequestException e) {
        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                                 null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException e) {
        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.NOT_FOUND.value(), e.getMessage(),
                                 null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        var details = e.getBindingResult()
                       .getFieldErrors()
                       .stream()
                       .collect(Collectors.toMap(FieldError::getField,
                                                 err -> Objects.requireNonNullElse(err.getDefaultMessage(),
                                                                                   "Invalid value"),
                                                 (msg1, msg2) -> msg1));

        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.BAD_REQUEST.value(),
                                 "Validation failed", details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(ConstraintViolationException e) {
        var details = e.getConstraintViolations()
                       .stream()
                       .collect(Collectors.toMap(violation -> {
                           String path = violation.getPropertyPath()
                                                  .toString();
                           return path.substring(path.lastIndexOf('.') + 1);
                       }, ConstraintViolation::getMessage, (msg1, msg2) -> msg1));

        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.BAD_REQUEST.value(),
                                 "Validation failed", details);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.BAD_REQUEST.value(),
                                 "Invalid value for parameter: %s".formatted(e.getName()), null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(AccessDeniedException e) {
        log.warn("Access denied: {}", e.getMessage());
        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.FORBIDDEN.value(), "Access denied",
                                 null);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.FORBIDDEN.value(), e.getMessage(),
                                 null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("Internal Server Error: ", e);

        return new ErrorResponse(Timestamp.valueOf(LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                 "An unexpected error occurred", null);
    }
}