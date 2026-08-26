package co.com.srdejo.usuarios.application.handler;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import co.com.srdejo.usuarios.application.dto.response.UserResponseDto;

import java.util.List;

public interface IOwnerHandler {

    void saveOwner(UserRequestDto userRequestDto);

    List<UserResponseDto> getAllOwners();
}
