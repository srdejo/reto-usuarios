package co.com.srdejo.usuarios.domain.api;

public interface IAuthenticationServicePort {

    String login(String email, String password);
}
