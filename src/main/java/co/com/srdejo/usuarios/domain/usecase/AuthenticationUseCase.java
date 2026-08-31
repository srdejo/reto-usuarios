package co.com.srdejo.usuarios.domain.usecase;

import co.com.srdejo.usuarios.domain.api.IAuthenticationServicePort;
import co.com.srdejo.usuarios.domain.exception.InvalidCredentialsException;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.ITokenGeneratorPort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;


public class AuthenticationUseCase implements IAuthenticationServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final ITokenGeneratorPort tokenGeneratorPort;

    public AuthenticationUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort, ITokenGeneratorPort tokenGeneratorPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenGeneratorPort = tokenGeneratorPort;
    }

    @Override
    public String login(String email, String password) {
        UserModel userModel = userPersistencePort.getUserByEmail(email);
        if (userModel == null || !passwordEncoderPort.matches(password, userModel.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return tokenGeneratorPort.generateToken(userModel);
    }
}
