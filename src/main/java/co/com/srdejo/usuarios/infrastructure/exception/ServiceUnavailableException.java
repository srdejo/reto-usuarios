package co.com.srdejo.usuarios.infrastructure.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
