package com.dasuanzhuang.halo.validate.api.impl;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;

import com.dasuanzhuang.halo.validate.api.Validation;

public class RequiredValidation implements Validation {

    @Override
    public Boolean validate(Object attrValue, Class<?> attrType, Object params) {
        if (attrValue == null) return false;
        if (ClassUtils.isAssignable(attrType, Object[].class)) return ((Object[]) attrValue).length > 0;
        else if (attrType == String.class) return ((String) attrValue).trim().length() > 0;
        else if (ClassUtils.isAssignable(attrType, Collection.class)) return !((Collection<?>) attrValue).isEmpty();
        else if (ClassUtils.isAssignable(attrType,  Map.class)) return !((Map<?, ?>) attrValue).isEmpty();
        return true;
    }
}
