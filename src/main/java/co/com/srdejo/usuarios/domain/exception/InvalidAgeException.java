package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public class InvalidAgeException extends InvalidException {

    public InvalidAgeException(ErrorCodesEnum errorCode) {
        super(errorCode);
    }

}
