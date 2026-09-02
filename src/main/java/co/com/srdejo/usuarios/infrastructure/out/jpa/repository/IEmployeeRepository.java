package co.com.srdejo.usuarios.infrastructure.out.jpa.repository;

import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
