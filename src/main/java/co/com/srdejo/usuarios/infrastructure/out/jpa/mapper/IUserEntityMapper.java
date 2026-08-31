package co.com.srdejo.usuarios.infrastructure.out.jpa.mapper;

import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = IRoleEntityMapper.class
)
public interface IUserEntityMapper {

    UserEntity toEntity(UserModel user);
    UserModel toUserModel(UserEntity objectEntity);
    List<UserModel> toUserModelList(List<UserEntity> userEntityList);

    default String map(PhoneModel phone) {
        return phone != null ? phone.phoneNumber() : null;
    }

    default PhoneModel map(String phone) {
        return phone != null ? new PhoneModel(phone) : null;
    }
}