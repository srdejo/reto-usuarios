package co.com.srdejo.usuarios.infrastructure.out.jpa.repository;

import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByName(String name);
}
