package com.article.api;

import com.article.api.exception.ForbiddenException;
import com.article.api.exception.RegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HanderlException {



    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> forbidenException(ForbiddenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<String> registerException(RegisterException ex) {
        return new ResponseEntity<>("Error en el registro: " +ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
