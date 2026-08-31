package co.com.srdejo.usuarios.infrastructure.out.jpa.adapter;

import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.domain.spi.IRolePersistencePort;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IRoleRepository;

public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    public RoleJpaAdapter(IRoleRepository roleRepository, IRoleEntityMapper roleEntityMapper) {
        this.roleRepository = roleRepository;
        this.roleEntityMapper = roleEntityMapper;
    }

    @Override
    public RoleModel findByName(String name) {
        RoleEntity roleEntity = roleRepository.findByName(name).orElseThrow(NoDataFoundException::new);
        return roleEntityMapper.toRoleModel(roleEntity);
    }
}
