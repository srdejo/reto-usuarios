package co.com.srdejo.usuarios.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Las contraseñas no coinciden";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
