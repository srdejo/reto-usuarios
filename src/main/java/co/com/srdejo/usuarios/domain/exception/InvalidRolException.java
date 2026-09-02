package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public class InvalidRolException extends InvalidException {

    public InvalidRolException(ErrorCodesEnum errorCode) {
        super(errorCode);
    }
}
