package com.dasuanzhuang.halo.validate.error;

/**
 * 验证异常
 * 
 * @author zhaohuiliang
 *
 */
public class ValidateException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -8278188977868217209L;

    public ValidateException() {
        super();
    }

    public ValidateException(String type, String message) {
        this(type, message, null);
    }

    public ValidateException(String attrName, Class<?> objClass) {
        this(attrName, objClass, null);
    }

    public ValidateException(String attrName, Class<?> attrType, Class<?> ObjClass, Throwable error) {
        super(String.format("[%s.%s(%s)]", ObjClass.getSimpleName(), attrName, attrType.getName()), error);
    }

    public ValidateException(String attrName, Class<?> objClass, Throwable error) {
        super(String.format("[attrName:%s,attrType:%s]", attrName, objClass.getName()), error);
    }

    public ValidateException(String rule, String message, Throwable error) {
        super(String.format("[rule:{%s},message:%s]", rule, message), error);
    }
}
