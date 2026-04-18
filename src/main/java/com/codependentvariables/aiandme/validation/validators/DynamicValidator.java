package com.codependentvariables.aiandme.validation.validators;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A flexible validator that allows each instance to define the validation function
 * @param <T>
 */
public class DynamicValidator<T> implements IValidator<T> {
    private final BiFunction<T, String, String> validationMethod;

    public DynamicValidator(BiFunction<T, String, String> validationMethod) {
        this.validationMethod = validationMethod;
    }

    @Override
    public String validate(T value, String display) {
        return validationMethod.apply(value, display);
    }
}
