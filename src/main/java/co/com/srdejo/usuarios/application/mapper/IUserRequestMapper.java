package co.com.srdejo.usuarios.application.mapper;

import co.com.srdejo.usuarios.application.dto.request.OwnerRequestDto;
import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {

    UserModel toUser(OwnerRequestDto ownerRequestDto);
    UserModel toUser(UserRequestDto userRequestDto);
    default PhoneModel map(String phone) {
        return phone != null ? new PhoneModel(phone) : null;
    }
}
