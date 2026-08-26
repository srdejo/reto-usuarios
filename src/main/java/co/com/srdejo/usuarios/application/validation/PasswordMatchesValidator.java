package co.com.srdejo.usuarios.application.validation;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
    implements ConstraintValidator<PasswordMatches, UserRequestDto> {

    @Override
    public boolean isValid(UserRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return true;
        }

        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
