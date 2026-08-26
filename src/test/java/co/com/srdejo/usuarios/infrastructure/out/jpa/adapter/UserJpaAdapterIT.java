package co.com.srdejo.usuarios.infrastructure.out.jpa.adapter;

import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test against a real (H2, in-memory) JPA repository, replacing Postgres for
 * fast, hermetic verification of persistence + entity mapping that unit-level mocks can't catch
 * (e.g. generated ids, enum column mapping, derived query methods).
 */
@DataJpaTest
class UserJpaAdapterIT {

    @Autowired
    private IUserRepository userRepository;

    private UserJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new UserJpaAdapter(userRepository, Mappers.getMapper(IUserEntityMapper.class));
    }

    private UserModel newOwner(String email) {
        UserModel user = new UserModel(null, "John", "Doe", "123456", new PhoneModel("+573005698325"),
                LocalDate.now().minusYears(25), email, "encodedPassword", null);
        user.becomeOwner();
        return user;
    }

    @Test
    void saveUser_thenGetByEmail_returnsThePersistedUserWithGeneratedId() {
        adapter.saveUser(newOwner("john@doe.com"));

        UserModel found = adapter.getUserByEmail("john@doe.com");

        assertThat(found.getId()).isNotNull();
        assertThat(found.getEmail()).isEqualTo("john@doe.com");
        assertThat(found.getPhone().phoneNumber()).isEqualTo("+573005698325");
        assertThat(found.getRole()).isEqualTo(RoleEnum.OWNER);
    }

    @Test
    void getUserByEmail_whenNoUserMatches_throwsNoDataFoundException() {
        assertThatThrownBy(() -> adapter.getUserByEmail("missing@doe.com"))
                .isInstanceOf(NoDataFoundException.class);
    }

    @Test
    void getAllUsersByRole_returnsOnlyUsersWithThatRole() {
        adapter.saveUser(newOwner("owner1@doe.com"));
        adapter.saveUser(newOwner("owner2@doe.com"));

        assertThat(adapter.getAllUsersByRole(RoleEnum.OWNER))
                .extracting(UserModel::getEmail)
                .containsExactlyInAnyOrder("owner1@doe.com", "owner2@doe.com");
    }

}
