package co.com.srdejo.usuarios.application.dto.request;

import co.com.srdejo.usuarios.application.validation.IPasswordConfirmation;
import co.com.srdejo.usuarios.application.validation.IPasswordMatches;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@IPasswordMatches
public record OwnerRequestDto(
    @NotBlank String name,
    @NotBlank String lastName,
    @NotBlank
    @Size(min = 6, max = 10)
    @Digits(fraction = 0, integer = 10) String document,
    @NotBlank
    @Size(min = 6, max = 13) String phone,
    @NotNull LocalDate birthDate,
    @NotBlank
    @Email(message = "El email no tiene una estructura válida") String email,
    @NotBlank String password,
    @NotBlank String confirmPassword
) implements IPasswordConfirmation { }
