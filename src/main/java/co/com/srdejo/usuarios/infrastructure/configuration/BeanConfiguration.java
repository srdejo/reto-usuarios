package co.com.srdejo.usuarios.infrastructure.configuration;

import co.com.srdejo.usuarios.domain.api.IUserServicePort;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;
import co.com.srdejo.usuarios.domain.usecase.UserUseCase;
import co.com.srdejo.usuarios.infrastructure.out.jpa.adapter.UserJpaAdapter;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IPasswordEncoderPort passwordEncoderPort;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), passwordEncoderPort);
    }

}