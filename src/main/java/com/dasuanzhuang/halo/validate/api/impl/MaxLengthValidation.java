package com.dasuanzhuang.halo.validate.api.impl;

import com.dasuanzhuang.halo.validate.api.Validation;

public class MaxLengthValidation implements Validation {

    @Override
    public Boolean validate(Object attrValue, Class<?> attrType, Object params) {
        Integer maxlength = Integer.valueOf((String) params);
        if (attrType == String.class) {
            String value = attrValue.toString();
            if (value.length() <= maxlength) { return true; }
        }
        return false;
    }

}
