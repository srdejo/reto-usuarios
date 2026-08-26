package co.com.srdejo.usuarios.application.validation;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordMatchesValidatorTest {

    private final PasswordMatchesValidator validator = new PasswordMatchesValidator();

    private UserRequestDto dtoWithPasswords(String password, String confirmPassword) {
        UserRequestDto dto = new UserRequestDto();
        dto.setPassword(password);
        dto.setConfirmPassword(confirmPassword);
        return dto;
    }

    @Test
    void isValid_whenPasswordsMatch_returnsTrue() {
        UserRequestDto dto = dtoWithPasswords("Secret123", "Secret123");

        assertThat(validator.isValid(dto, null)).isTrue();
    }

    @Test
    void isValid_whenPasswordsDiffer_returnsFalse() {
        UserRequestDto dto = dtoWithPasswords("Secret123", "Different123");

        assertThat(validator.isValid(dto, null)).isFalse();
    }

    @Test
    void isValid_whenPasswordIsNull_returnsTrue() {
        // Delegated to @NotBlank on password; avoids a NullPointerException here.
        UserRequestDto dto = dtoWithPasswords(null, "Secret123");

        assertThat(validator.isValid(dto, null)).isTrue();
    }
}
