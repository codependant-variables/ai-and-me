package com.codependentvariables.aiandme.validation.validators;

import java.util.Objects;

public class NotNullValidator<T> implements IValidator<T> {
    @Override
    public String validate(T value, String display) {
        return Objects.isNull(value) ? String.format("%s is required.", display) : null;
    }
}
