package co.com.srdejo.usuarios.infrastructure.configuration;

import co.com.srdejo.usuarios.domain.api.IAuthenticationServicePort;
import co.com.srdejo.usuarios.domain.api.IOwnerServicePort;
import co.com.srdejo.usuarios.domain.api.IUserServicePort;
import co.com.srdejo.usuarios.domain.spi.*;
import co.com.srdejo.usuarios.domain.usecase.AuthenticationUseCase;
import co.com.srdejo.usuarios.domain.usecase.OwnerUseCase;
import co.com.srdejo.usuarios.domain.usecase.UserUseCase;
import co.com.srdejo.usuarios.infrastructure.out.feign.adapter.RestaurantClientAdapter;
import co.com.srdejo.usuarios.infrastructure.out.feign.client.RestaurantClient;
import co.com.srdejo.usuarios.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import co.com.srdejo.usuarios.infrastructure.out.jpa.adapter.UserJpaAdapter;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IEmployeeRepository;
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
    private final IEmployeeRepository employeeRepository;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRoleRepository  roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    private final RestaurantClient restaurantClient;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper, employeeRepository);
    }

    @Bean
    public IRolePersistencePort  rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IRestaurantClientPort restaurantClientPort() {
        return new RestaurantClientAdapter(restaurantClient);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), passwordEncoderPort, rolePersistencePort(), restaurantClientPort());
    }

    @Bean
    public IOwnerServicePort ownerServicePort() {
        return new OwnerUseCase(userPersistencePort(), passwordEncoderPort, rolePersistencePort());
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