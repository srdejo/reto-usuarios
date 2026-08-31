package co.com.srdejo.usuarios.infrastructure.configuration;

import co.com.srdejo.usuarios.domain.api.IAuthenticationServicePort;
import co.com.srdejo.usuarios.domain.api.IUserServicePort;
import co.com.srdejo.usuarios.domain.spi.*;
import co.com.srdejo.usuarios.domain.usecase.AuthenticationUseCase;
import co.com.srdejo.usuarios.domain.usecase.UserUseCase;
import co.com.srdejo.usuarios.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import co.com.srdejo.usuarios.infrastructure.out.jpa.adapter.UserJpaAdapter;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IRoleRepository;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import co.com.srdejo.usuarios.infrastructure.out.jwt.JwtTokenAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRoleRepository  roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IRolePersistencePort  rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), passwordEncoderPort, rolePersistencePort());
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort() {
        return new AuthenticationUseCase(userPersistencePort(), passwordEncoderPort, tokenGeneratorPort());
    }

    @Bean
    public ITokenGeneratorPort tokenGeneratorPort() {
        return new JwtTokenAdapter(jwtSecret, jwtExpiration);
    }

    @Bean
    public ITokenValidatorPort tokenValidatorPort() {
        return new JwtTokenAdapter(jwtSecret, jwtExpiration);
    }


}