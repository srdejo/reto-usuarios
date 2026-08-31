package co.com.srdejo.usuarios.domain.model;

import lombok.Getter;

@Getter
public class RoleModel {

    private final Long id;
    private final String name;
    private final String description;

    public RoleModel(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
