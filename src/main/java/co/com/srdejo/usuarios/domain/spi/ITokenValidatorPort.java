package co.com.srdejo.usuarios.domain.spi;

import co.com.srdejo.usuarios.domain.model.UserModel;

public interface ITokenValidatorPort {

    UserModel validateToken(String token);

}
