package com.utp.Aychow.Usuarios_MS.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class RolNombreValidator implements ConstraintValidator<RolNombre, String> {
    private final List<String> validRoles = Arrays.asList("Admin", "Cliente");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validRoles.contains(value);
    }
}