package co.com.srdejo.usuarios.domain.usecase;

import co.com.srdejo.usuarios.domain.api.IOwnerServicePort;
import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IRolePersistencePort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;

import java.util.List;

public class OwnerUseCase implements IOwnerServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRolePersistencePort rolePersistencePort;

    public OwnerUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort,
                         IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void createOwner(UserModel userModel) {
        userModel.becomeOwner(rolePersistencePort.findByName(RoleEnum.OWNER.name()));
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
