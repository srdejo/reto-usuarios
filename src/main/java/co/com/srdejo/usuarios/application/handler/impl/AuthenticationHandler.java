package co.com.srdejo.usuarios.application.handler.impl;

import co.com.srdejo.usuarios.application.dto.request.LoginRequestDto;
import co.com.srdejo.usuarios.application.dto.response.LoginResponseDto;
import co.com.srdejo.usuarios.application.handler.IAuthenticationHandler;
import co.com.srdejo.usuarios.domain.api.IAuthenticationServicePort;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Service
@AllArgsConstructor
public class AuthenticationHandler implements IAuthenticationHandler {

    private final IAuthenticationServicePort authenticationService;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return new LoginResponseDto( authenticationService.login(loginRequestDto.email(), loginRequestDto.password())) ;
    }
}
