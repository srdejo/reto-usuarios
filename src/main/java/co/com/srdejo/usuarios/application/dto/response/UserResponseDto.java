package co.com.srdejo.usuarios.application.dto.response;

import co.com.srdejo.usuarios.domain.model.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String name;
    private String lastName;
    private String document;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private RoleEnum role;

}
