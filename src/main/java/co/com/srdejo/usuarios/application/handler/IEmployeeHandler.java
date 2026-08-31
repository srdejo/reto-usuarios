package co.com.srdejo.usuarios.application.handler;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;

public interface IEmployeeHandler {

    void saveEmployee(UserRequestDto userRequestDto);
}
