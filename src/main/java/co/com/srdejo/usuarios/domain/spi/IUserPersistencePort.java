package co.com.srdejo.usuarios.domain.spi;

import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.UserModel;

import java.util.List;

public interface IUserPersistencePort {
    void saveUser(UserModel userModel);
    void saveEmployee(UserModel userModel, Long restaurantId);
    UserModel getUserByEmail(String email);
    List<UserModel> getAllUsersByRole(RoleEnum role);
    UserModel getByIdAndRole(Long id, RoleEnum roleEnum);
}