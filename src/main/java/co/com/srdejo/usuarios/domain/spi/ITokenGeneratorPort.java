package co.com.srdejo.usuarios.domain.spi;

import co.com.srdejo.usuarios.domain.model.UserModel;

public interface ITokenGeneratorPort {
    String generateToken(UserModel user);
}
