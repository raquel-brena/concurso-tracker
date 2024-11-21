package com.rb.web2.infra.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rb.web2.infra.RestMessage.RestErrorMessage;
import com.rb.web2.shared.exceptions.BadRequestException;
import com.rb.web2.shared.exceptions.ForbbidenException;
import com.rb.web2.shared.exceptions.NotFoundException;

@ControllerAdvice
public class RestExeceptionHandler extends ResponseEntityExceptionHandler { 
    
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RestErrorMessage> notFoundHandler(NotFoundException exception) { 
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new RestErrorMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<RestErrorMessage> badRequestHandler(BadRequestException exception) { 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new RestErrorMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(ForbbidenException.class)
    private ResponseEntity<RestErrorMessage> forbbidenHandler(ForbbidenException exception) { 
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(new RestErrorMessage(HttpStatus.FORBIDDEN.value(), exception.getMessage()));
    }
}