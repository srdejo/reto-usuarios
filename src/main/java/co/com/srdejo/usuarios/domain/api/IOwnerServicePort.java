package co.com.srdejo.usuarios.domain.api;

import co.com.srdejo.usuarios.domain.model.UserModel;

import java.util.List;

public interface IOwnerServicePort {

    void createOwner(UserModel userModel);

    List<UserModel> getAllOwners();

    UserModel getOwner(Long id);
}
