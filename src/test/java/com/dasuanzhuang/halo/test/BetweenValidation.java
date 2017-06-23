package com.dasuanzhuang.halo.test;

import com.dasuanzhuang.halo.validate.annotation.ValidationRegister;
import com.dasuanzhuang.halo.validate.api.Validation;

/**
 * Created by zhaohuiliang on 2017/6/16.
 */
@ValidationRegister("between")
public class BetweenValidation implements Validation {
    @Override
    public Boolean validate(Object attrValue, Class<?> attrType, Object params) {
        String[] strs = (String[]) params;
        Integer value = (Integer) attrValue;
        if (Integer.parseInt(strs[0]) <= value && Integer.parseInt(strs[1]) >= value) {
            return true;
        }
        return false;
    }
}
