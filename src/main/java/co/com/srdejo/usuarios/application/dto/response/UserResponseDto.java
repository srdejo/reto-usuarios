package co.com.srdejo.usuarios.application.dto.response;

import co.com.srdejo.usuarios.domain.model.RoleEnum;

import java.time.LocalDate;

public record UserResponseDto(
        Long id,
        String name,
        String lastName,
        String document,
        String phone,
        LocalDate birthDate,
        String email,
        RoleEnum role
) { }
