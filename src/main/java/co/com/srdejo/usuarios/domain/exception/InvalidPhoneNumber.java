package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public class InvalidPhoneNumber extends InvalidException {

    public InvalidPhoneNumber(ErrorCodesEnum errorCode) {
        super(errorCode);
    }

}
