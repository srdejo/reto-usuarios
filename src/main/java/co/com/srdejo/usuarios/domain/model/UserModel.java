package co.com.srdejo.usuarios.domain.model;

import co.com.srdejo.usuarios.domain.exception.InvalidAgeException;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
public class UserModel {

    private static final int ADULT_AGE = 18;
    private static final String INVALID_AGE_MESSAGE = "No cumple con la edad minima requerida";

    private final Long id;
    private final String name;
    private final String lastName;
    private final String document;
    private final PhoneModel phone;
    private final LocalDate birthDate;
    private final String email;
    private String password;
    private RoleEnum role;


    public UserModel(Long id, String name, String lastName, String document, PhoneModel phone,
                     LocalDate birthDate, String email, String password,
                     RoleEnum role) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public void becomeOwner() {
        validateAdult();
        this.role = RoleEnum.OWNER;
    }

    public void setEncryptedPassword(String password) {
        this.password = password;
    }

    private void validateAdult() {
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age < ADULT_AGE) {
            throw new InvalidAgeException(INVALID_AGE_MESSAGE);
        }
    }
}
