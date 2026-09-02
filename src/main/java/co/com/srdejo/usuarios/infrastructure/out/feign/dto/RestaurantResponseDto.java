package co.com.srdejo.usuarios.infrastructure.out.feign.dto;

public record RestaurantResponseDto(
        Long id,
        String name,
        String address,
        Long ownerId,
        String phoneNumber,
        String urlLogo,
        String taxId
) {
}
