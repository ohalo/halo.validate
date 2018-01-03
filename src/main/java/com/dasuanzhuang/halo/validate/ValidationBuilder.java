package com.dasuanzhuang.halo.validate;

import com.dasuanzhuang.halo.validate.api.Validation;
import com.dasuanzhuang.halo.validate.load.ValidateProperties;

public class ValidationBuilder {

    private static ValidationBuilder builder = new ValidationBuilder();

    public static ValidationBuilder builder() {
        return builder;
    }

    public Validation createValidation(String name) throws InstantiationException, IllegalAccessException {
        return ValidateProperties.getValidationRegister().get(name);
    };
}
