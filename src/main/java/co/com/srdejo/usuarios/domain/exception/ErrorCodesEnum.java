package co.com.srdejo.usuarios.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodesEnum {

    INVALID_AGE("ERR01", "No cumple con la edad minima requerida"),
    INVALID_PHONE_EMPTY("ERR02", "El numero de contacto no puede estar vacio"),
    INVALID_PHONE_FORMAT("ERR03", "El numero de contacto solo puede contener numeros y el simbolo +"),
    INVALID_CREDENTIALS("AUTH1", "Invalid email or password"),
    INVALID_TOKEN("AUTH2", "Token de autenticacion invalido o expirado"),
    OWNER_NOT_AUTHORIZED("AUTH3", "No tienes permisos para crear empleados en este restaurante"),
    INVALID_RESTAURANT_ID("ERR04", "El restaurante indicado no existe"),
    INVALID_ROLE("ERR05", "El role indicado no coincide con la accion solicitada"),
    SERVICE_UNAVAILABLE("HTTP1", "El servicio %s no responde"),
    VALIDATION_ERROR("VAL01", "Error de validacion en los datos enviados");

    private final String code;
    private final String description;

}
