package co.com.srdejo.usuarios.application.handler.impl;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import co.com.srdejo.usuarios.application.handler.IUserHandler;
import co.com.srdejo.usuarios.application.mapper.IUserRequestMapper;
import co.com.srdejo.usuarios.domain.api.IUserServicePort;
import co.com.srdejo.usuarios.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    @Override
    public void saveEmployee(UserRequestDto userRequestDto, Long restaurantId) {
        UserModel userModel = userRequestMapper.toUser(userRequestDto);
        userServicePort.createEmployee(userModel, userRequestDto.roleId(), restaurantId);
    }
}
