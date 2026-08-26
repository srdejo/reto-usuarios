package co.com.srdejo.usuarios.domain.model;

import co.com.srdejo.usuarios.domain.exception.InvalidPhoneNumber;

public record PhoneModel(String phoneNumber) {

    public PhoneModel {
        if( phoneNumber == null || phoneNumber.isBlank()){
            throw new InvalidPhoneNumber("El numero de contacto no puede estar vacio");
        }

        String number = phoneNumber.startsWith("+")
                ? phoneNumber.substring(1)
                : phoneNumber;

        if (number.isEmpty() ||
                !number.chars().allMatch(Character::isDigit)) {
            throw new InvalidPhoneNumber(
                    "El número de contacto solo puede contener números y el símbolo +"
            );
        }
    }
}
