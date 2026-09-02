package co.com.srdejo.usuarios.domain.api;

import co.com.srdejo.usuarios.domain.model.UserModel;

public interface IUserServicePort {

    void createEmployee(UserModel userModel, Long roleId, Long restaurantId);
}
