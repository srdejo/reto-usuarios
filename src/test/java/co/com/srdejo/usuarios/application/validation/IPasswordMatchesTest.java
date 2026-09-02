package co.com.srdejo.usuarios.application.validation;

import co.com.srdejo.usuarios.application.dto.request.OwnerRequestDto;
import co.com.srdejo.usuarios.application.validation.impl.PasswordMatches;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class IPasswordMatchesTest {

    private final PasswordMatches validator = new PasswordMatches();

    private OwnerRequestDto dtoWithPasswords(String password, String confirmPassword) {
        return new OwnerRequestDto(
                "John", "Doe", "123456", "+573005698325",
                LocalDate.of(1990, 1, 1), "john@doe.com", password, confirmPassword);
    }

    @Test
    void isValid_whenPasswordsMatch_returnsTrue() {
        OwnerRequestDto dto = dtoWithPasswords("Secret123", "Secret123");

        assertThat(validator.isValid(dto, null)).isTrue();
    }

    @Test
    void isValid_whenPasswordsDiffer_returnsFalse() {
        OwnerRequestDto dto = dtoWithPasswords("Secret123", "Different123");

        assertThat(validator.isValid(dto, null)).isFalse();
    }

    @Test
    void isValid_whenPasswordIsNull_returnsTrue() {
        // Delegated to @NotBlank on password; avoids a NullPointerException here.
        OwnerRequestDto dto = dtoWithPasswords(null, "Secret123");

        assertThat(validator.isValid(dto, null)).isTrue();
    }
}
