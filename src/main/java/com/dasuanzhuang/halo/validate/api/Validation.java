package com.dasuanzhuang.halo.validate.api;

public interface Validation {

    /**
     * 
     * @param attrValue
     *            字段的值
     * @param attrType
     *            字段类型
     * @param params
     *            规则中的参数 例如：mixlength:[5,6]
     * @return
     */
    Boolean validate(Object attrValue, Class<?> attrType, Object params);

}
