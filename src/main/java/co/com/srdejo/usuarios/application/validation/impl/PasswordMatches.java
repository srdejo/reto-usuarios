package co.com.srdejo.usuarios.application.validation.impl;

import co.com.srdejo.usuarios.application.validation.IPasswordConfirmation;
import co.com.srdejo.usuarios.application.validation.IPasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatches
    implements ConstraintValidator<IPasswordMatches, IPasswordConfirmation> {

    @Override
    public boolean isValid(IPasswordConfirmation dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        if (dto.password() == null || dto.confirmPassword() == null) {
            return true;
        }

        return dto.password().equals(dto.confirmPassword());
    }
}
