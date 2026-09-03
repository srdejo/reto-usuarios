package co.com.srdejo.usuarios.infrastructure.out.security;

import co.com.srdejo.usuarios.domain.spi.IAuthenticatedUserPort;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityContextUserAdapter implements IAuthenticatedUserPort {

    @Override
    public Long getAuthenticatedUserId() {
        return (Long) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}
