package com.codependentvariables.aiandme.modules.validation.validators;

import java.util.Objects;

/**
 * Requires the object reference to not be null. nuff said?
 * @param <T>
 */
public class NotNullValidator<T> implements IValidator<T> {
    @Override
    public String validate(T value, String display) {
        return Objects.isNull(value) ? String.format("%s is required.", display) : null;
    }
}
