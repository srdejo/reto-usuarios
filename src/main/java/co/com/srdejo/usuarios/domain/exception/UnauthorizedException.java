package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends InvalidException {

    public UnauthorizedException(ErrorCodesEnum errorCode) {
        super(errorCode);
    }

}
