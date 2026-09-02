package co.com.srdejo.usuarios.infrastructure.input.rest;

import co.com.srdejo.usuarios.application.dto.request.OwnerRequestDto;
import co.com.srdejo.usuarios.application.dto.response.UserResponseDto;
import co.com.srdejo.usuarios.application.handler.IOwnerHandler;
import co.com.srdejo.usuarios.domain.exception.ErrorCodesEnum;
import co.com.srdejo.usuarios.domain.exception.InvalidAgeException;
import co.com.srdejo.usuarios.domain.spi.ITokenValidatorPort;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import co.com.srdejo.usuarios.infrastructure.exceptionhandler.ControllerAdvisor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Slice test: exercises the real Bean Validation pipeline on {@link OwnerRequestDto}
 * plus {@link ControllerAdvisor}, which unit tests on the handler/use case cannot cover.
 */
@WebMvcTest(controllers = OwnerRestController.class)
class OwnerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IOwnerHandler ownerHandler;

    @MockitoBean
    private ITokenValidatorPort tokenValidatorPort;

    private static final String ADULT_BIRTH_DATE = LocalDate.now().minusYears(25).toString();

    private String validRequestJson(String birthDate) {
        return """
                {
                  "name": "John",
                  "lastName": "Doe",
                  "document": "123456",
                  "phone": "+573005698325",
                  "birthDate": "%s",
                  "email": "john@doe.com",
                  "password": "Secret123",
                  "confirmPassword": "Secret123"
                }
                """.formatted(birthDate);
    }

    @Test
    void saveOwner_withValidPayload_returns201AndInvokesHandler() throws Exception {
        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson(ADULT_BIRTH_DATE)))
                .andExpect(status().isCreated());

        verify(ownerHandler).saveOwner(any());
    }

    // One representative case per @Valid rule from AC #1/#2: required fields, email
    // structure, phone length, numeric document and password confirmation.
    static Stream<Arguments> invalidPayloads() {
        return Stream.of(
                Arguments.of("blank name", (java.util.function.UnaryOperator<String>)
                        json -> json.replace("\"John\"", "\"\"")),
                Arguments.of("invalid email", (java.util.function.UnaryOperator<String>)
                        json -> json.replace("john@doe.com", "not-an-email")),
                Arguments.of("phone over 13 chars", (java.util.function.UnaryOperator<String>)
                        json -> json.replace("+573005698325", "+5730056983259999")),
                Arguments.of("non-numeric document", (java.util.function.UnaryOperator<String>)
                        json -> json.replace("\"123456\"", "\"12A456\"")),
                Arguments.of("mismatched passwords", (java.util.function.UnaryOperator<String>)
                        json -> json.replace("\"confirmPassword\": \"Secret123\"", "\"confirmPassword\": \"Different123\""))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidPayloads")
    void saveOwner_withInvalidPayload_returns400AndHandlerIsNeverCalled(
            String caseName, java.util.function.UnaryOperator<String> mutation) throws Exception {
        String payload = mutation.apply(validRequestJson(ADULT_BIRTH_DATE));

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(ownerHandler);
    }

    @Test
    void saveOwner_whenUserIsUnderage_returns400WithDomainMessage() throws Exception {
        // AC #4: bean validation passes (birthDate is present) but the domain rule rejects a minor.
        String minorBirthDate = LocalDate.now().minusYears(10).toString();
        doThrow(new InvalidAgeException(ErrorCodesEnum.INVALID_AGE))
                .when(ownerHandler).saveOwner(any());

        mockMvc.perform(post("/api/v1/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRequestJson(minorBirthDate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No cumple con la edad minima requerida"));
    }

    @Test
    void getOwnerById_returnsOkWithOwner() throws Exception {
        UserResponseDto responseDto = new UserResponseDto(1L, "John", "Doe", "123456",
                "+573005698325", LocalDate.now().minusYears(25), "john@doe.com", null);
        when(ownerHandler.getOwnerById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void getOwnerById_whenOwnerDoesNotExist_returns404() throws Exception {
        when(ownerHandler.getOwnerById(99L)).thenThrow(new NoDataFoundException());

        mockMvc.perform(get("/api/v1/owners/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllOwners_returnsOkWithOwnersList() throws Exception {
        UserResponseDto responseDto = new UserResponseDto(1L, "John", "Doe", "123456",
                "+573005698325", LocalDate.now().minusYears(25), "john@doe.com", null);
        when(ownerHandler.getAllOwners()).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/v1/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    void getAllOwners_whenNoOwnersExist_returns404() throws Exception {
        when(ownerHandler.getAllOwners()).thenThrow(new NoDataFoundException());

        mockMvc.perform(get("/api/v1/owners"))
                .andExpect(status().isNotFound());
    }
}
