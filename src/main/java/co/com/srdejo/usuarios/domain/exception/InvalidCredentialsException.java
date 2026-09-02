package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends InvalidException {

    public InvalidCredentialsException(ErrorCodesEnum errorCode) {
        super(errorCode);
    }

}
