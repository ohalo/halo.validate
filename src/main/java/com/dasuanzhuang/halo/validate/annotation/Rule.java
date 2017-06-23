package com.dasuanzhuang.halo.validate.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface Rule {

    /**
     * #Rule("required:true")
     * 
     * @return
     */
    String value() default "";

    String message() default "";
}
