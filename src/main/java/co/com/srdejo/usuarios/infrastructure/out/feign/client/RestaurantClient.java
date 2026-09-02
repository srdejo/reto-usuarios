package co.com.srdejo.usuarios.infrastructure.out.feign.client;

import co.com.srdejo.usuarios.infrastructure.out.feign.dto.RestaurantResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RestaurantClient", url = "${feign-url.restaurants}")
public interface RestaurantClient {

    @GetMapping("/{id}")
    RestaurantResponseDto getRestaurantById(@PathVariable Long id);
}
