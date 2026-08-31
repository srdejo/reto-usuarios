package co.com.srdejo.usuarios.infrastructure.configuration;

import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IRolePersistencePort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Regression test: getAllUsersByRole throws NoDataFoundException when no rows match
 * (it was written for the getAllOwners 404 case), which crashed application startup the
 * first time the seeder ran against an empty users table.
 */
@ExtendWith(MockitoExtension.class)
class AdminSeederTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    private AdminSeeder adminSeeder;

    @BeforeEach
    void setUp() {
        adminSeeder = new AdminSeeder(userPersistencePort, rolePersistencePort, passwordEncoderPort);
        ReflectionTestUtils.setField(adminSeeder, "adminEmail", "admin@pragma.com");
        ReflectionTestUtils.setField(adminSeeder, "adminPassword", "Admin123*");
    }

    @Test
    void run_whenNoAdminExistsYet_seedsTheFirstAdminInsteadOfCrashing() {
        when(userPersistencePort.getAllUsersByRole(RoleEnum.ADMIN)).thenThrow(new NoDataFoundException());
        when(passwordEncoderPort.encode("Admin123*")).thenReturn("encodedPassword");
        when(rolePersistencePort.findByName("ADMIN")).thenReturn(new RoleModel(1L, "ADMIN", null));

        adminSeeder.run();

        verify(userPersistencePort).saveUser(any(UserModel.class));
    }

    @Test
    void run_whenAnAdminAlreadyExists_doesNothing() {
        UserModel existingAdmin = mock(UserModel.class);
        when(userPersistencePort.getAllUsersByRole(RoleEnum.ADMIN)).thenReturn(List.of(existingAdmin));

        adminSeeder.run();

        verify(userPersistencePort, never()).saveUser(any());
        verifyNoInteractions(passwordEncoderPort);
    }
}
