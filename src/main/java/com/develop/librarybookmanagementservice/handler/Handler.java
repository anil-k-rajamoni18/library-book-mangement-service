package com.develop.librarybookmanagementservice.handler;

import com.develop.librarybookmanagementservice.dtos.ErrorResponse;
import com.develop.librarybookmanagementservice.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class Handler extends ResponseEntityExceptionHandler {
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                               final HttpHeaders headers,
                                                               final HttpStatusCode status,
                                                               final WebRequest request) {
        log.error("MethodArgumentNotValidException thrown", ex);
        final List<ErrorResponse.ValidationErrors> errors = buildErrors(ex.getBindingResult().getFieldErrors());
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("BR_100")
                .description("Bad Request | Validation failed")
                .errors(errors)
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(final Exception ex) {
        log.error("Exception thrown", ex);
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("500")
                .description(ex.getMessage())
                .build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<Object> handleBookException(final BookException ex) {
        log.error("BookException thrown", ex);
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getCode())
                .description(ex.getMessage())
                .build();
        return ResponseEntity.status(getHttpStatusCode(ex.getCode())).body(errorResponse);
    }

    private HttpStatus getHttpStatusCode(String code) {
        return switch (code) {
            case "400" -> BAD_REQUEST;
            case "500" -> INTERNAL_SERVER_ERROR;
            case "404" -> NOT_FOUND;
            default -> INTERNAL_SERVER_ERROR;
        };
    }


    private List<ErrorResponse.ValidationErrors> buildErrors(final List<FieldError> fieldErrors) {
        final List<ErrorResponse.ValidationErrors> errors = new ArrayList<>();
        fieldErrors.forEach(error -> {
            final ErrorResponse.ValidationErrors errMsg = ErrorResponse.ValidationErrors.builder()
                    .field(error.getField())
                    .description(error.getDefaultMessage())
                    .rejectedValue(null != error.getRejectedValue() ? error.getRejectedValue().toString() : "")
                    .build();
            errors.add(errMsg);
        });
        return errors;
    }
}
