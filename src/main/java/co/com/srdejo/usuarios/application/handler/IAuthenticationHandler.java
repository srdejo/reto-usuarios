package co.com.srdejo.usuarios.application.handler;

import co.com.srdejo.usuarios.application.dto.request.LoginRequestDto;
import co.com.srdejo.usuarios.application.dto.response.LoginResponseDto;

public interface IAuthenticationHandler {

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
