package co.com.srdejo.usuarios.domain.spi;

public interface IPasswordEncoderPort {

    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
