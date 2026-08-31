package co.com.srdejo.usuarios.infrastructure.out.jpa.mapper;

import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Regression test: RoleEntity used to lack Lombok getters/setters and RoleModel only had a
 * single-arg constructor, so MapStruct silently produced a RoleModel with id/name = null for
 * every role read from the database (breaking JWT role claims and role-based authorization).
 */
class IRoleEntityMapperImplTest {

    private final IRoleEntityMapper mapper = new IRoleEntityMapperImpl();

    @Test
    void toRoleModelAndBack_preservesIdNameAndDescription() {
        RoleEntity entity = new RoleEntity(1L, "ADMIN", "Administrador del sistema", null);

        RoleModel roleModel = mapper.toRoleModel(entity);
        assertThat(roleModel.getId()).isEqualTo(1L);
        assertThat(roleModel.getName()).isEqualTo("ADMIN");
        assertThat(roleModel.getDescription()).isEqualTo("Administrador del sistema");

        RoleEntity roundTripped = mapper.toEntity(roleModel);
        assertThat(roundTripped.getId()).isEqualTo(1L);
        assertThat(roundTripped.getName()).isEqualTo("ADMIN");
    }
}
