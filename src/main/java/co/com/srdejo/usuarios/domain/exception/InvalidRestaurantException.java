package co.com.srdejo.usuarios.domain.exception;

import lombok.Getter;

@Getter
public class InvalidRestaurantException extends InvalidException {


    public InvalidRestaurantException(ErrorCodesEnum errorCode) {
        super(errorCode);
    }

}
