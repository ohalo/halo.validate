package com.dasuanzhuang.halo.validate;

import com.dasuanzhuang.halo.validate.annotation.ValidationRegisterScan;
import com.dasuanzhuang.halo.validate.api.Validation;

public class ValidationBuilder {

    private static ValidationBuilder builder = new ValidationBuilder();

    public static ValidationBuilder builder() {
        return builder;
    }

    public Validation createValidation(String name) throws InstantiationException, IllegalAccessException {
        Class<?> validateClass = ValidationRegisterScan.get(name);
        if (validateClass == null) { return null; }
        return (Validation) validateClass.newInstance();
    };
}
