package co.com.srdejo.usuarios.infrastructure.input.rest;

import co.com.srdejo.usuarios.application.dto.request.CustomerRequestDto;
import co.com.srdejo.usuarios.application.dto.request.LoginRequestDto;
import co.com.srdejo.usuarios.application.dto.response.LoginResponseDto;
import co.com.srdejo.usuarios.application.handler.IAuthenticationHandler;
import co.com.srdejo.usuarios.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final IAuthenticationHandler authenticationHandler;
    private final IUserHandler userHandler;

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user using their email and password and returns an access token."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid email or password", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto userRequestDto) {
        return ResponseEntity.ok(authenticationHandler.login(userRequestDto));
    }


    @Operation(summary = "Signup a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer already exists", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid CustomerRequestDto customerRequestDto) {
        userHandler.saveCustomer(customerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
