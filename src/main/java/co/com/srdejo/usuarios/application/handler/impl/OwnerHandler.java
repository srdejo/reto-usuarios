package co.com.srdejo.usuarios.application.handler.impl;

import co.com.srdejo.usuarios.application.dto.request.OwnerRequestDto;
import co.com.srdejo.usuarios.application.dto.response.UserResponseDto;
import co.com.srdejo.usuarios.application.handler.IOwnerHandler;
import co.com.srdejo.usuarios.application.mapper.IUserRequestMapper;
import co.com.srdejo.usuarios.application.mapper.IUserResponseMapper;
import co.com.srdejo.usuarios.domain.api.IOwnerServicePort;
import co.com.srdejo.usuarios.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerHandler implements IOwnerHandler {


    private final IOwnerServicePort ownerServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void saveOwner(OwnerRequestDto ownerRequestDto) {
        UserModel userModel = userRequestMapper.toUser(ownerRequestDto);
        ownerServicePort.createOwner(userModel);
    }

    @Override
    public List<UserResponseDto> getAllOwners() {
        return userResponseMapper.toUsers(ownerServicePort.getAllOwners());
    }

    @Override
    public UserResponseDto getOwnerById(Long id) {
        return userResponseMapper.toUserResponseDto(ownerServicePort.getOwner(id));
    }
}
