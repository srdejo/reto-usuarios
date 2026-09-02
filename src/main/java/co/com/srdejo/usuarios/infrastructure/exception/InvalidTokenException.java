package co.com.srdejo.usuarios.infrastructure.exception;

import co.com.srdejo.usuarios.domain.exception.ErrorCodesEnum;
import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private final ErrorCodesEnum error;

    public InvalidTokenException(ErrorCodesEnum errorCode) {
        super(errorCode.getDescription());
        this.error = errorCode;
    }

}
