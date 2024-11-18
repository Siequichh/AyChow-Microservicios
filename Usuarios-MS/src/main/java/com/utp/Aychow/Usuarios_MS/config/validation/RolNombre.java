package com.utp.Aychow.Usuarios_MS.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RolNombreValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RolNombre {
    String message() default "Rol no válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}