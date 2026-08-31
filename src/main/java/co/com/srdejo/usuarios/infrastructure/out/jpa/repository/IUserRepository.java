package co.com.srdejo.usuarios.infrastructure.out.jpa.repository;

import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByRole_Name(String role);

    UserEntity findByIdAndRole_Name(Long id, String role);
}