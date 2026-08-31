package co.com.srdejo.usuarios.domain.usecase;

import co.com.srdejo.usuarios.domain.exception.InvalidAgeException;
import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IRolePersistencePort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userPersistencePort, passwordEncoderPort, rolePersistencePort);
    }

    private UserModel adultUser() {
        return new UserModel(null, "John", "Doe", "123456", new PhoneModel("+573005698325"),
                LocalDate.now().minusYears(25), "john@doe.com", "rawPassword", null);
    }

    private UserModel minorUser() {
        return new UserModel(null, "John", "Doe", "123456", new PhoneModel("+573005698325"),
                LocalDate.now().minusYears(10), "john@doe.com", "rawPassword", null);
    }

    @Test
    void createOwner_whenUserIsAdult_assignsOwnerRoleEncryptsPasswordAndPersists() {
        UserModel user = adultUser();
        RoleModel ownerRole = new RoleModel(1L, "OWNER", null);
        when(rolePersistencePort.findByName(RoleEnum.OWNER.name())).thenReturn(ownerRole);
        when(passwordEncoderPort.encode("rawPassword")).thenReturn("encodedPassword");

        userUseCase.createOwner(user);

        assertThat(user.getRole()).isEqualTo(ownerRole);
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void createOwner_whenUserIsMinor_throwsAndNeverEncodesOrPersists() {
        UserModel user = minorUser();

        assertThatThrownBy(() -> userUseCase.createOwner(user))
                .isInstanceOf(InvalidAgeException.class);

        verifyNoInteractions(passwordEncoderPort);
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void getAllOwners_queriesPersistencePortFilteringByOwnerRole() {
        List<UserModel> owners = List.of(adultUser());
        when(userPersistencePort.getAllUsersByRole(RoleEnum.OWNER)).thenReturn(owners);

        List<UserModel> result = userUseCase.getAllOwners();

        assertThat(result).isEqualTo(owners);
        verify(userPersistencePort).getAllUsersByRole(RoleEnum.OWNER);
    }
}
