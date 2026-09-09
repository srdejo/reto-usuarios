package co.com.srdejo.usuarios.application.handler;

import co.com.srdejo.usuarios.application.dto.request.CustomerRequestDto;
import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import co.com.srdejo.usuarios.application.dto.response.EmployeeResponseDto;

public interface IUserHandler {

    void saveEmployee(UserRequestDto userRequestDto, Long restaurantId);

    void saveCustomer(CustomerRequestDto customerRequestDto);

    EmployeeResponseDto getAuthenticatedEmployee();
}
