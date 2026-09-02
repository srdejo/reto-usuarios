package co.com.srdejo.usuarios.infrastructure.out.feign.adapter;

import co.com.srdejo.usuarios.domain.exception.ErrorCodesEnum;
import co.com.srdejo.usuarios.domain.exception.InvalidRestaurantException;
import co.com.srdejo.usuarios.domain.spi.IRestaurantClientPort;
import co.com.srdejo.usuarios.infrastructure.exception.ServiceUnavailableException;
import co.com.srdejo.usuarios.infrastructure.out.feign.client.RestaurantClient;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestaurantClientAdapter implements IRestaurantClientPort {

    private final RestaurantClient restaurantClient;

    @Override
    public Long getOwnerId(Long restaurantId) {
        try {
            return restaurantClient.getRestaurantById(restaurantId).ownerId();
        } catch (feign.FeignException.NotFound ex) {
            throw new InvalidRestaurantException(ErrorCodesEnum.INVALID_RESTAURANT_ID);
        } catch (feign.RetryableException ex) {
            throw new ServiceUnavailableException("restaurants-service", ex);
        }
    }
}
