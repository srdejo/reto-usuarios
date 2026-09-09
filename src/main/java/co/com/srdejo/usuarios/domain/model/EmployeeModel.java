package co.com.srdejo.usuarios.domain.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EmployeeModel {

    private final Long id;
    private final String name;
    private final String lastName;
    private final String document;
    private final PhoneModel phone;
    private final LocalDate birthDate;
    private final String email;
    private final RoleModel role;
    private final Long restaurantId;

    public EmployeeModel(UserModel userModel, Long restaurantId) {
        this.id = userModel.getId();
        this.name = userModel.getName();
        this.lastName = userModel.getLastName();
        this.document = userModel.getDocument();
        this.phone = userModel.getPhone();
        this.birthDate = userModel.getBirthDate();
        this.email = userModel.getEmail();
        this.role = userModel.getRole();
        this.restaurantId = restaurantId;
    }
}
