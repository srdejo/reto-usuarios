package co.com.srdejo.usuarios.application.handler;

import co.com.srdejo.usuarios.application.dto.request.OwnerRequestDto;
import co.com.srdejo.usuarios.application.dto.response.UserResponseDto;

import java.util.List;

public interface IOwnerHandler {

    void saveOwner(OwnerRequestDto ownerRequestDto);

    List<UserResponseDto> getAllOwners();

    UserResponseDto getOwnerById(Long id);
}
