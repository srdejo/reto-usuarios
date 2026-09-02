package co.com.srdejo.usuarios.infrastructure.out.jpa.adapter;

import co.com.srdejo.usuarios.domain.model.RoleEnum;
import co.com.srdejo.usuarios.domain.model.UserModel;
import co.com.srdejo.usuarios.domain.spi.IUserPersistencePort;
import co.com.srdejo.usuarios.infrastructure.exception.NoDataFoundException;
import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.EmployeeEntity;
import co.com.srdejo.usuarios.infrastructure.out.jpa.entity.UserEntity;
import co.com.srdejo.usuarios.infrastructure.out.jpa.mapper.IUserEntityMapper;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IEmployeeRepository;
import co.com.srdejo.usuarios.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IEmployeeRepository employeeRepository;


    @Override
    public void saveUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        userEntity = userRepository.save(userEntity);
        userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public void saveEmployee(UserModel userModel, Long restaurantId) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        userEntity = userRepository.save(userEntity);

        employeeRepository.save(new EmployeeEntity(userEntity.getId(), restaurantId));
    }

    @Override
    public UserModel getUserByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if  (userEntityOptional.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserModel(userEntityOptional.get());
    }

    @Override
    public List<UserModel> getAllUsersByRole(RoleEnum roleEnum) {
        List<UserEntity> entityList = userRepository.findAllByRole_Name(roleEnum.name());
        if (entityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserModelList(entityList);
    }

    @Override
    public UserModel getByIdAndRole(Long id, RoleEnum roleEnum) {
        return userEntityMapper.toUserModel(userRepository.findByIdAndRole_Name(id, roleEnum.name()));
    }


}