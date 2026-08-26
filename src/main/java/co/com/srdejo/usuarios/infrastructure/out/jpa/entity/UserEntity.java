package co.com.srdejo.usuarios.infrastructure.out.jpa.entity;

import co.com.srdejo.usuarios.domain.model.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String lastName;

    @Column(length = 15)
    private String document;

    @Column(length = 13)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;
}
