package com.rb.web2.infra.exceptionHandler;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rb.web2.shared.RestMessage.RestErrorMessage;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.ForbbidenException;
import com.rb.web2.shared.exceptions.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
        NotFoundException.class,
        BadRequestException.class,
        ForbbidenException.class,
        InvalidFormatException.class,
        DateTimeParseException.class
    })
    private ResponseEntity<?> handleCustomExceptions(Exception ex, WebRequest request) {
        if (ex instanceof NotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
        } else if (ex instanceof BadRequestException || ex instanceof InvalidFormatException || ex instanceof DateTimeParseException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage("Dados inválidos fornecidos: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } else if (ex instanceof ForbbidenException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RestErrorMessage( ex.getMessage(), HttpStatus.FORBIDDEN.value()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage( "Erro inesperado: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
