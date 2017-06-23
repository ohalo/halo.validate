package com.dasuanzhuang.halo.validate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;

import com.dasuanzhuang.halo.validate.annotation.Rule;
import com.dasuanzhuang.halo.validate.annotation.Validate;
import com.dasuanzhuang.halo.validate.api.Validation;
import com.dasuanzhuang.halo.validate.error.ValidateException;
import com.dasuanzhuang.halo.validate.yaml.ValidateYaml;

public class BaseValidateService implements IValidateService {

    private void parseRuleAndValidate(Rule rule, Object value, Class<?> type, String attrName) throws InstantiationException, IllegalAccessException, ValidateException {
        String ruleValue = rule.value();
        String[] ruleValues = ruleValue.split(":");
        Validation validator = ValidationBuilder.builder().createValidation(ruleValues[0]);
        if (validator == null) { throw new ValidateException(attrName, ruleValue + "规则错误，请检查！"); }
        String message = rule.message();
        if (message == null || message.trim().equals("")) {
            message = ValidateYaml.getInstance().getMessage().get(ruleValues[0]);
        }

        Boolean flag = false;
        if (ruleValues.length > 1) {
            String params = ruleValues[1];
            if (params != null && !params.trim().equals("")) {
                if (params.indexOf("[") > -1 && params.lastIndexOf("]") > -1) {
                    params = params.replaceAll("\\[", "").replaceAll("]", "");
                    String[] paramsStr = params.split(",");
                    if (message != null && !message.trim().equals("")) {
                        for (int i = 0; i < paramsStr.length; i++) {
                            message = message.replace("{" + i + "}", paramsStr[i]);
                        }
                    }
                    flag = validator.validate(value, type, paramsStr);
                } else {
                    flag = validator.validate(value, type, ruleValues[1]);
                    if (message != null && !message.trim().equals("")) {
                        message = message.replace("{0}", ruleValues[1]);
                    }
                }
            } else {
                throw new ValidateException(attrName, ruleValue + "规则错误，请检查！");
            }
        } else {
            flag = validator.validate(value, type, null);
        }

        if (flag == null || !flag) { throw new ValidateException(ruleValue, message); }
    }

    @Override
    public void validate(Object obj) throws ValidateException {

        if (obj == null) { throw new ValidateException("null", NullPointerException.class); }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Validate validate = fields[i].getAnnotation(Validate.class);

                if (validate == null) {
                    continue;
                }

                Object value = fields[i].get(obj);
                Rule[] rules = validate.rules();
                Class<?> valueType = fields[i].getType();

                for (Rule rule : rules) {
                    parseRuleAndValidate(rule, value, valueType, fields[i].getName());
                }

                Class<?> objClass = validate.objClass();

                if (objClass == Object.class || value == null) {
                    continue;
                }

                if (ClassUtils.isAssignable(valueType, Map.class)) {
                    Map<?, ?> map = (Map<?, ?>) value;
                    Set<?> keys = map.keySet();
                    for (Iterator<?> iterator = keys.iterator(); iterator.hasNext();) {
                        Object key = iterator.next();
                        if (key.getClass() == objClass) {
                            validate(key);
                        }
                        Object mapValue = map.get(key);
                        if (mapValue.getClass() == objClass) {
                            validate(mapValue);
                        }
                    }
                } else if (ClassUtils.isAssignable(valueType, Collection.class)) {
                    Collection<?> c = (Collection<?>) value;
                    for (Object object : c) {
                        if (object.getClass() == objClass) {
                            validate(object);
                        }
                    }
                } else if (ClassUtils.isAssignable(valueType, Object[].class)) {
                    Object[] objs = (Object[]) value;
                    for (Object object : objs) {
                        if (object.getClass() == objClass) {
                            validate(object);
                        }
                    }
                } else {
                    if (value.getClass() == objClass) {
                        validate(value);
                    }
                }
            } catch (Exception e) {
                throw new ValidateException(fields[i].getName(), fields[i].getType(), obj.getClass(), e);
            }
        }
    }
}
