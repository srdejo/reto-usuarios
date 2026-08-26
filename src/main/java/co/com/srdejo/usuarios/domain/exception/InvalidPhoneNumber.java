package co.com.srdejo.usuarios.domain.exception;

public class InvalidPhoneNumber extends RuntimeException {
    public InvalidPhoneNumber(String message) {
        super(message);
    }
}
