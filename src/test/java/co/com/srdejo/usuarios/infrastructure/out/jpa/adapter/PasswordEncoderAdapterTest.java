package co.com.srdejo.usuarios.infrastructure.out.jpa.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncoderAdapterTest {

    private final PasswordEncoderAdapter adapter = new PasswordEncoderAdapter(new BCryptPasswordEncoder());

    @Test
    void encode_producesABcryptHashDistinctFromTheRawPassword() {
        String encoded = adapter.encode("Secret123");

        assertThat(encoded).isNotEqualTo("Secret123");
        assertThat(encoded).matches("^\\$2[aby]\\$\\d{2}\\$.{53}$");
    }

    @Test
    void matches_roundTripsWithCorrectPasswordAndRejectsWrongOne() {
        String encoded = adapter.encode("Secret123");

        assertThat(adapter.matches("Secret123", encoded)).isTrue();
        assertThat(adapter.matches("WrongPassword", encoded)).isFalse();
    }
}
