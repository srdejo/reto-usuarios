package co.com.srdejo.usuarios.infrastructure.exceptionhandler;

import co.com.srdejo.usuarios.domain.exception.*;
import co.com.srdejo.usuarios.infrastructure.exception.InvalidTokenException;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import co.com.srdejo.usuarios.infrastructure.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    private static final String CODE = "code";
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    @ExceptionHandler({InvalidAgeException.class, InvalidPhoneNumber.class, InvalidRestaurantException.class,
            InvalidRolException.class})
    public ResponseEntity<Map<String, Object>> handleDomainException(InvalidException exception) {
        return getMapResponseEntity(HttpStatus.BAD_REQUEST, exception.getError());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialsException(InvalidException exception) {
        return getMapResponseEntity(HttpStatus.UNAUTHORIZED, exception.getError());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTokenException(InvalidTokenException exception) {
        return getMapResponseEntity(HttpStatus.UNAUTHORIZED, exception.getError());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException exception) {
        return getMapResponseEntity(HttpStatus.FORBIDDEN, exception.getError());
    }

    @ExceptionHandler({feign.RetryableException.class, ServiceUnavailableException.class})
    public ResponseEntity<Map<String, Object>> handleFeignRetryable(Exception exception) {
        String message = String.format(ErrorCodesEnum.SERVICE_UNAVAILABLE.getDescription(), exception.getMessage());
        log.error(exception.getMessage(), exception);

        return getMapResponseEntity(HttpStatus.SERVICE_UNAVAILABLE, ErrorCodesEnum.SERVICE_UNAVAILABLE.getCode(), message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return getMapResponseEntity(HttpStatus.BAD_REQUEST, ErrorCodesEnum.VALIDATION_ERROR.getCode(), message);
    }

    @NonNull
    private ResponseEntity<Map<String, Object>> getMapResponseEntity(HttpStatus status, ErrorCodesEnum error) {
        return getMapResponseEntity(status, error.getCode(), error.getDescription());
    }

    @NonNull
    private ResponseEntity<Map<String, Object>> getMapResponseEntity(HttpStatus status, String code, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CODE, code);
        body.put(MESSAGE, message);
        body.put(TIMESTAMP, Instant.now());

        return ResponseEntity.status(status).body(body);
    }

}
