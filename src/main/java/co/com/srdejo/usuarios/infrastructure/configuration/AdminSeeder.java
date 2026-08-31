package co.com.srdejo.usuarios.infrastructure.configuration;

import co.com.srdejo.usuarios.domain.model.PhoneModel;
import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IRolePersistencePort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    @Value("${admin.seed.email:admin@pragma.com}")
    private String adminEmail;

    @Value("${admin.seed.password:Admin123*}")
    private String adminPassword;

    @Override
    public void run(String @NonNull ... args) {

        boolean adminExists;

        try {
            adminExists = !userPersistencePort.getAllUsersByRole(RoleEnum.ADMIN).isEmpty();
        } catch (NoDataFoundException exception) {
            adminExists = false;
        }
        if (adminExists) {
            return;
        }

        UserModel admin = new UserModel(
                null,
                "Admin",
                "Pragma",
                "0000000000",
                new PhoneModel("+573000000000"),
                LocalDate.of(1990, 1, 1),
                adminEmail,
                passwordEncoderPort.encode(adminPassword),
                rolePersistencePort.findByName(RoleEnum.ADMIN.name())
        );

        userPersistencePort.saveUser(admin);
        log.warn("Usuario ADMIN sembrado: {} (cambiar password en producción)", adminEmail);
    }
}
