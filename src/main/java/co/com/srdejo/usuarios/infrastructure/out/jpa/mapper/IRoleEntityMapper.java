package co.com.srdejo.usuarios.infrastructure.out.jpa.mapper;

import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRoleEntityMapper {

    RoleModel toRoleModel(RoleEntity entity);
    RoleEntity toEntity(RoleModel value);
}
