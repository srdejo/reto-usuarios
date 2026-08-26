package co.com.srdejo.usuarios.domain.model;

import co.com.srdejo.usuarios.domain.exception.InvalidPhoneNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneModelTest {

    @Test
    void constructor_withLeadingPlusSign_createsPhoneModel() {
        PhoneModel phone = new PhoneModel("+573005698325");

        assertThat(phone.phoneNumber()).isEqualTo("+573005698325");
    }

    @Test
    void constructor_withNullValue_throwsInvalidPhoneNumber() {
        assertThatThrownBy(() -> new PhoneModel(null))
                .isInstanceOf(InvalidPhoneNumber.class);
    }

    @Test
    void constructor_withOnlyPlusSign_throwsInvalidPhoneNumber() {
        // Edge case: "+" strips to an empty digit string.
        assertThatThrownBy(() -> new PhoneModel("+"))
                .isInstanceOf(InvalidPhoneNumber.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"300 569 8325", "phoneNumber", "30+05698325"})
    void constructor_withNonDigitCharacters_throwsInvalidPhoneNumber(String invalidPhone) {
        // Covers letters, spaces, and a '+' anywhere other than the leading position.
        assertThatThrownBy(() -> new PhoneModel(invalidPhone))
                .isInstanceOf(InvalidPhoneNumber.class);
    }
}
