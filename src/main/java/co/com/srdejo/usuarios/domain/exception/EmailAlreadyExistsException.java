package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends InvalidException {

    public EmailAlreadyExistsException(ErrorCodesEnum errorCode) {
        super(errorCode);
    }
}
