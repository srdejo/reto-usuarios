package co.com.srdejo.usuarios.domain.model;

import co.com.srdejo.usuarios.domain.exception.ErrorCodesEnum;
import co.com.srdejo.usuarios.domain.exception.InvalidPhoneNumber;

public record PhoneModel(String phoneNumber) {

    public PhoneModel {
        if( phoneNumber == null || phoneNumber.isBlank()){
            throw new InvalidPhoneNumber(ErrorCodesEnum.INVALID_PHONE_EMPTY);
        }

        String number = phoneNumber.startsWith("+")
                ? phoneNumber.substring(1)
                : phoneNumber;

        if (number.isEmpty() ||
                !number.chars().allMatch(Character::isDigit)) {
            throw new InvalidPhoneNumber(ErrorCodesEnum.INVALID_PHONE_FORMAT);
        }
    }
}
