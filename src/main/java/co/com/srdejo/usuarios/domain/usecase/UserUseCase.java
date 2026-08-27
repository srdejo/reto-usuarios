package co.com.srdejo.usuarios.domain.usecase;

import co.com.srdejo.usuarios.domain.api.IUserServicePort;
import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void createOwner(UserModel userModel) {
        userModel.becomeOwner();
        userModel.setEncryptedPassword(passwordEncoderPort.encode(userModel.getPassword()));
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public List<UserModel> getAllOwners() {
        return userPersistencePort.getAllUsersByRole(RoleEnum.OWNER);
    }

    @Override
    public UserModel getOwner(Long id) {
        return userPersistencePort.getByIdAndRole(id, RoleEnum.OWNER);
    }


}