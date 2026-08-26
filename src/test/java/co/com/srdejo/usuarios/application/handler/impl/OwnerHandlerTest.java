package co.com.srdejo.usuarios.application.handler.impl;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import co.com.srdejo.usuarios.application.dto.response.UserResponseDto;
import co.com.srdejo.usuarios.application.mapper.IUserRequestMapper;
import co.com.srdejo.usuarios.application.mapper.IUserResponseMapper;
import co.com.srdejo.usuarios.domain.api.IUserServicePort;
import co.com.srdejo.usuarios.domain.exception.InvalidAgeException;
import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserResponseMapper userResponseMapper;

    private OwnerHandler ownerHandler;

    @BeforeEach
    void setUp() {
        ownerHandler = new OwnerHandler(userServicePort, userRequestMapper, userResponseMapper);
    }

    private UserModel userModel() {
        return new UserModel(null, "John", "Doe", "123456", new PhoneModel("+573005698325"),
                LocalDate.now().minusYears(25), "john@doe.com", "Secret123", null);
    }

    @Test
    void saveOwner_mapsRequestDtoAndDelegatesToCreateOwner() {
        UserRequestDto dto = new UserRequestDto();
        UserModel mappedModel = userModel();
        when(userRequestMapper.toUser(dto)).thenReturn(mappedModel);

        ownerHandler.saveOwner(dto);

        verify(userServicePort).createOwner(mappedModel);
    }

    @Test
    void saveOwner_whenServiceRejectsUnderageUser_propagatesException() {
        // The handler must not swallow domain rule violations coming from the use case.
        UserRequestDto dto = new UserRequestDto();
        UserModel mappedModel = userModel();
        when(userRequestMapper.toUser(dto)).thenReturn(mappedModel);
        doThrow(new InvalidAgeException("No cumple con la edad minima requerida"))
                .when(userServicePort).createOwner(mappedModel);

        assertThatThrownBy(() -> ownerHandler.saveOwner(dto))
                .isInstanceOf(InvalidAgeException.class);
    }

    @Test
    void getAllOwners_mapsServiceResultToResponseDtoList() {
        List<UserModel> owners = List.of(userModel());
        UserResponseDto responseDto = new UserResponseDto();
        when(userServicePort.getAllOwners()).thenReturn(owners);
        when(userResponseMapper.toUsers(owners)).thenReturn(List.of(responseDto));

        List<UserResponseDto> result = ownerHandler.getAllOwners();

        assertThat(result).containsExactly(responseDto);
    }
}
