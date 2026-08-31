package co.com.srdejo.usuarios.application.mapper;

import co.com.srdejo.usuarios.application.dto.response.UserResponseDto;
import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {

    @Mapping(target = "role", source = "role.name")
    UserResponseDto toUserResponseDto(UserModel userModel);

    default String map(PhoneModel phone) {
        return phone != null ? phone.phoneNumber() : null;
    }

    List<UserResponseDto> toUsers(List<UserModel> allOwners);
}
