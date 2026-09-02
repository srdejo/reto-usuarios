package co.com.srdejo.usuarios.domain.spi;

import co.com.srdejo.usuarios.domain.model.RoleModel;

public interface IRolePersistencePort {

    RoleModel findByName(String name);

    RoleModel findById(Long roleId);
}
