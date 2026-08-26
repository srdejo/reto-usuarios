package co.com.srdejo.usuarios.domain.model;

import co.com.srdejo.usuarios.domain.exception.InvalidAgeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserModelTest {

    private UserModel userWithBirthDate(LocalDate birthDate) {
        return new UserModel(null, "John", "Doe", "123456", new PhoneModel("+573005698325"),
                birthDate, "john@doe.com", "rawPassword", null);
    }

    @Test
    void createOwner_whenUserTurnsAdultToday_setsRoleToOwner() {
        // Edge case: exactly 18 years old today, the boundary must be inclusive.
        UserModel user = userWithBirthDate(LocalDate.now().minusYears(18));

        user.becomeOwner();

        assertThat(user.getRole()).isEqualTo(RoleEnum.OWNER);
    }

    @Test
    void createOwner_whenUserTurnsAdultTomorrow_throwsInvalidAgeExceptionAndDoesNotChangeRole() {
        // Edge case: one day short of 18, the most common off-by-one bug in age checks.
        UserModel user = userWithBirthDate(LocalDate.now().minusYears(18).plusDays(1));

        assertThatThrownBy(user::becomeOwner)
                .isInstanceOf(InvalidAgeException.class);
        assertThat(user.getRole()).isNull();
    }

    @Test
    void setEncryptedPassword_replacesRawPasswordWithEncodedValue() {
        UserModel user = userWithBirthDate(LocalDate.now().minusYears(20));

        user.setEncryptedPassword("$2a$10$encodedHash");

        assertThat(user.getPassword()).isEqualTo("$2a$10$encodedHash");
    }
}
