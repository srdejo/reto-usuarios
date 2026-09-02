package co.com.srdejo.usuarios.application.validation;

import co.com.srdejo.usuarios.application.validation.impl.PasswordMatches;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatches.class)
@Documented
public @interface IPasswordMatches {
    String message() default "Las contraseñas no coinciden";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
