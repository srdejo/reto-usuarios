package co.com.srdejo.usuarios.application.dto.request;

import co.com.srdejo.usuarios.application.validation.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@PasswordMatches
public class UserRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    @Size(min = 6, max = 10)
    @Digits(fraction = 0, integer = 10)
    private String document;
    @NotBlank
    @Size(min = 6, max = 13)
    private String phone;
    @NotNull
    private LocalDate birthDate;
    @NotBlank
    @Email(message = "El email no tiene una estructura válida")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;

}
