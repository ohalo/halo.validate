package com.dasuanzhuang.halo.test;

import com.dasuanzhuang.halo.validate.annotation.ValidationRegister;
import com.dasuanzhuang.halo.validate.api.Validation;

@ValidationRegister("checkUsernameExists")
public class UsernameValidation implements Validation {

    @Override
    public Boolean validate(Object attrValue, Class<?> attrType, Object params) {
        if (!attrValue.equals("admin")) { return true; }
        return false;
    }
}
