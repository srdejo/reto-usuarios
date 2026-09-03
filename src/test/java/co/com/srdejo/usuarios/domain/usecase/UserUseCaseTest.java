package co.com.srdejo.usuarios.domain.usecase;

import co.com.srdejo.usuarios.domain.exception.EmailAlreadyExistsException;
import co.com.srdejo.usuarios.domain.exception.UnauthorizedException;
import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IAuthenticatedUserPort;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IRestaurantClientPort;
import co.com.srdejo.usuarios.domain.spi.IRolePersistencePort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

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

    @Mock
    private IRestaurantClientPort restaurantClientPort;

    @Mock
    private IAuthenticatedUserPort authenticatedUserPort;

    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userPersistencePort, passwordEncoderPort, rolePersistencePort, restaurantClientPort, authenticatedUserPort);
    }

    private UserModel adultUser() {
        return new UserModel(null, "John", "Doe", "123456", new PhoneModel("+573005698325"),
                LocalDate.now().minusYears(25), "john@doe.com", "rawPassword", null);
    }

    @Test
    void createEmployee_whenAuthenticatedOwnerOwnsRestaurant_assignsRoleEncryptsPasswordAndPersists() {
        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(99L);
        UserModel user = adultUser();
        RoleModel employeeRole = new RoleModel(2L, "EMPLOYEE", null);
        when(restaurantClientPort.getOwnerId(10L)).thenReturn(99L);
        when(rolePersistencePort.findById(2L)).thenReturn(employeeRole);
        when(passwordEncoderPort.encode("rawPassword")).thenReturn("encodedPassword");

        userUseCase.createEmployee(user, 2L, 10L);

        assertThat(user.getRole()).isEqualTo(employeeRole);
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        verify(userPersistencePort).saveEmployee(user, 10L);
    }

    @Test
    void createEmployee_whenAuthenticatedOwnerDoesNotOwnRestaurant_throwsAndNeverPersists() {
        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(1L);
        UserModel user = adultUser();
        when(restaurantClientPort.getOwnerId(10L)).thenReturn(99L);

        assertThatThrownBy(() -> userUseCase.createEmployee(user, 2L, 10L))
                .isInstanceOf(UnauthorizedException.class);

        verifyNoInteractions(passwordEncoderPort);
        verify(userPersistencePort, never()).saveEmployee(any(), any());
    }

    @Test
    void createEmployee_whenEmailAlreadyExists_throwsAndNeverPersists() {
        when(authenticatedUserPort.getAuthenticatedUserId()).thenReturn(99L);
        UserModel user = adultUser();
        when(restaurantClientPort.getOwnerId(10L)).thenReturn(99L);
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userUseCase.createEmployee(user, 2L, 10L))
                .isInstanceOf(EmailAlreadyExistsException.class);

        verifyNoInteractions(passwordEncoderPort);
        verify(userPersistencePort, never()).saveEmployee(any(), any());
    }

    @Test
    void createCustomer_whenEmailIsNew_assignsRoleEncryptsPasswordAndPersists() {
        UserModel user = adultUser();
        RoleModel customerRole = new RoleModel(3L, "CUSTOMER", null);
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(false);
        when(rolePersistencePort.findByName("CUSTOMER")).thenReturn(customerRole);
        when(passwordEncoderPort.encode("rawPassword")).thenReturn("encodedPassword");

        userUseCase.createCustomer(user);

        assertThat(user.getRole()).isEqualTo(customerRole);
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void createCustomer_whenEmailAlreadyExists_throwsAndNeverPersists() {
        UserModel user = adultUser();
        when(userPersistencePort.existsByEmail(user.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userUseCase.createCustomer(user))
                .isInstanceOf(EmailAlreadyExistsException.class);

        verifyNoInteractions(passwordEncoderPort);
        verify(userPersistencePort, never()).saveUser(any());
    }
}
