package co.com.srdejo.usuarios.domain.usecase;

import co.com.srdejo.usuarios.domain.api.IUserServicePort;
import co.com.srdejo.usuarios.domain.exception.EmailAlreadyExistsException;
import co.com.srdejo.usuarios.domain.exception.ErrorCodesEnum;
import co.com.srdejo.usuarios.domain.exception.InvalidRolException;
import co.com.srdejo.usuarios.domain.exception.UnauthorizedException;
import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.RoleModel;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IAuthenticatedUserPort;
import co.com.srdejo.usuarios.domain.spi.IPasswordEncoderPort;
import co.com.srdejo.usuarios.domain.spi.IRestaurantClientPort;
import co.com.srdejo.usuarios.domain.spi.IRolePersistencePort;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IRolePersistencePort rolePersistencePort;
    private final IRestaurantClientPort restaurantClientPort;
    private final IAuthenticatedUserPort authenticatedUserPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoderPort,
                        IRolePersistencePort rolePersistencePort, IRestaurantClientPort restaurantClientPort,
                        IAuthenticatedUserPort authenticatedUserPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.rolePersistencePort = rolePersistencePort;
        this.restaurantClientPort = restaurantClientPort;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Override
    public void createEmployee(UserModel userModel, Long roleId, Long restaurantId) {
        Long authenticatedOwnerId = authenticatedUserPort.getAuthenticatedUserId();
        Long restaurantOwnerId = restaurantClientPort.getOwnerId(restaurantId);
        if (!restaurantOwnerId.equals(authenticatedOwnerId)) {
            throw new UnauthorizedException(ErrorCodesEnum.OWNER_NOT_AUTHORIZED);
        }
        if (userPersistencePort.existsByEmail(userModel.getEmail())) {
            throw new EmailAlreadyExistsException(ErrorCodesEnum.EMAIL_ALREADY_EXISTS);
        }
        RoleModel role = rolePersistencePort.findById(roleId);
        if (! role.getName().equals(RoleEnum.EMPLOYEE.name())) {
            throw new InvalidRolException(ErrorCodesEnum.INVALID_ROLE);
        }
        userModel.assignRole(role);
        userModel.setEncryptedPassword(passwordEncoderPort.encode(userModel.getPassword()));
        userPersistencePort.saveEmployee(userModel, restaurantId);
    }

    @Override
    public void createCustomer(UserModel userModel) {
        if (userPersistencePort.existsByEmail(userModel.getEmail())) {
            throw new EmailAlreadyExistsException(ErrorCodesEnum.EMAIL_ALREADY_EXISTS);
        }
        RoleModel role = rolePersistencePort.findByName(RoleEnum.CUSTOMER.name());
        userModel.assignRole(role);
        userModel.setEncryptedPassword(passwordEncoderPort.encode(userModel.getPassword()));
        userPersistencePort.saveUser(userModel);
    }
}
