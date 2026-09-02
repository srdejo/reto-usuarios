package co.com.srdejo.usuarios.application.handler;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;

public interface IUserHandler {

    void saveEmployee(UserRequestDto userRequestDto, Long restaurantId);
}
