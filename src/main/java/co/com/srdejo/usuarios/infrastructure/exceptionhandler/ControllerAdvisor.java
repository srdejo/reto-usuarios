package co.com.srdejo.usuarios.infrastructure.exceptionhandler;

import co.com.srdejo.usuarios.domain.exception.InvalidAgeException;
import co.com.srdejo.usuarios.domain.exception.InvalidPhoneNumber;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler({InvalidAgeException.class, InvalidPhoneNumber.class})
    public ResponseEntity<Map<String, String>> handleInvalidAgeException(
            RuntimeException exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, exception.getMessage()));
    }

    
}