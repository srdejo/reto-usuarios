package co.com.srdejo.usuarios.infrastructure.configuration;

import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.ITokenValidatorPort;
import co.com.srdejo.usuarios.domain.exception.ErrorCodesEnum;
import co.com.srdejo.usuarios.infrastructure.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Regression test for the bug where the filter built the authority with
 * {@code tokenModel.getRole()} (the RoleModel object, missing toString()) instead of
 * {@code .getName()}, so no {@code @PreAuthorize("hasRole(...)")} could ever match.
 */
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private ITokenValidatorPort tokenValidatorPort;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(tokenValidatorPort);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_withValidToken_grantsAuthorityNamedAfterTheRole() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        UserModel userModel = new UserModel(1L, null, null, null, null, null,
                "admin@pragma.com", null, new RoleModel(1L, "ADMIN", null));
        when(tokenValidatorPort.validateToken("valid-token")).thenReturn(userModel);

        filter.doFilterInternal(request, response, filterChain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getAuthorities())
                .extracting(Object::toString)
                .containsExactly("ROLE_ADMIN");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_clearsSecurityContext() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(tokenValidatorPort.validateToken("invalid-token")).thenThrow(new InvalidTokenException(ErrorCodesEnum.INVALID_TOKEN));

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }
}
