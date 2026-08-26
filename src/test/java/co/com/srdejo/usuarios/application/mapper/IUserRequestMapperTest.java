package co.com.srdejo.usuarios.application.mapper;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import co.com.srdejo.usuarios.domain.model.UserModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class IUserRequestMapperTest {

    private final IUserRequestMapper mapper = Mappers.getMapper(IUserRequestMapper.class);

    @Test
    void toUser_mapsAllRequestFieldsIntoDomainModelAndLeavesRoleUnset() {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("John");
        dto.setLastName("Doe");
        dto.setDocument("123456");
        dto.setPhone("+573005698325");
        dto.setBirthDate(LocalDate.of(1990, 1, 1));
        dto.setEmail("john@doe.com");
        dto.setPassword("Secret123");
        dto.setConfirmPassword("Secret123");

        UserModel userModel = mapper.toUser(dto);

        assertThat(userModel.getId()).isNull();
        assertThat(userModel.getName()).isEqualTo("John");
        assertThat(userModel.getDocument()).isEqualTo("123456");
        assertThat(userModel.getPhone().phoneNumber()).isEqualTo("+573005698325");
        assertThat(userModel.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(userModel.getEmail()).isEqualTo("john@doe.com");
        assertThat(userModel.getPassword()).isEqualTo("Secret123");
        // Role is only ever assigned by UserModel#createOwner, never by the mapper.
        assertThat(userModel.getRole()).isNull();
    }
}
