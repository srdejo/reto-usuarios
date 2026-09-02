package co.com.srdejo.usuarios.infrastructure.input.rest;

import co.com.srdejo.usuarios.application.dto.request.UserRequestDto;
import co.com.srdejo.usuarios.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Add a new employee to a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists", content = @Content)
    })
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{restaurantId}/employees")
    public ResponseEntity<Void> saveEmployee(@PathVariable Long restaurantId,
                                          @RequestBody @Valid UserRequestDto userRequestDto) {
        userHandler.saveEmployee(userRequestDto, restaurantId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
