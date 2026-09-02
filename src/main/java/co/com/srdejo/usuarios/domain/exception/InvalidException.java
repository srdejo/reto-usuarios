package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public abstract class InvalidException extends RuntimeException {

    private final ErrorCodesEnum error;

    protected InvalidException(ErrorCodesEnum errorCode) {
        super(errorCode.getDescription());
        this.error = errorCode;
    }
}
