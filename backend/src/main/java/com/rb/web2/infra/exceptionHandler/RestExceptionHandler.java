package com.rb.web2.infra.exceptionHandler;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rb.web2.shared.RestMessage.RestErrorMessage;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.ForbbidenException;
import com.rb.web2.shared.exceptions.NotFoundException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder errorMessage = new StringBuilder("Erro de validação: ");
        
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append(" ");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(errorMessage.toString(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestErrorMessage> handleTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String errorMessage = "Erro de tipo: Parâmetro '" + ex.getName() + "' deve ser do tipo " + ex.getRequiredType().getName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(errorMessage, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = "Erro de formato: Corpo da requisição inválido.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(errorMessage, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({
            NotFoundException.class,
            BadRequestException.class,
            ForbbidenException.class,
            InvalidFormatException.class,
            DateTimeParseException.class,
            ConstraintViolationException.class
    })
    private ResponseEntity<?> handleCustomExceptions(Exception ex, WebRequest request) {
        if (ex instanceof NotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
        } else if (ex instanceof BadRequestException || ex instanceof InvalidFormatException
                || ex instanceof DateTimeParseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(
                    "Dados inválidos fornecidos: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } else if (ex instanceof ForbbidenException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new RestErrorMessage(ex.getMessage(), HttpStatus.FORBIDDEN.value()));
        } else if (ex instanceof ConstraintViolationException constraintViolationException) {
            StringBuilder errorMessage = new StringBuilder("Dados inválidos fornecidos: ");
            constraintViolationException.getConstraintViolations().forEach(violation -> {
                errorMessage.append(violation.getMessage()).append(" ");
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RestErrorMessage(errorMessage.toString(), HttpStatus.BAD_REQUEST.value()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(
                    "Erro inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
