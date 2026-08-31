package co.com.srdejo.usuarios.infrastructure.out.jwt;

import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.ITokenGeneratorPort;
import co.com.srdejo.usuarios.domain.spi.ITokenValidatorPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTokenAdapter implements ITokenGeneratorPort, ITokenValidatorPort {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtTokenAdapter(
           String secret,
           long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
        this.expiration = expiration;
    }

    @Override
    public String generateToken(UserModel user) {

        Date issuedAt = new Date();
        Date expirationDate = new Date(
                issuedAt.getTime() + expiration
        );

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole().getName())
                .claim("email", user.getEmail())
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public UserModel validateToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new UserModel(
                Long.valueOf(claims.getSubject()),
                null,
                null,
                null,
                null,
                null,
                claims.get("email", String.class),
                null,
                new RoleModel(null, claims.get("role", String.class), null)
        );
    }
}
