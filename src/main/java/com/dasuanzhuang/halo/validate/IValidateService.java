package com.dasuanzhuang.halo.validate;

import com.dasuanzhuang.halo.validate.error.ValidateException;

public interface IValidateService {
    void validate(Object obj) throws ValidateException;
}
