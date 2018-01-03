package com.dasuanzhuang.halo.validate.load;

import java.util.HashMap;
import java.util.Map;

import com.dasuanzhuang.halo.validate.api.Validation;

public class ValidateProperties {

    private static Map<String, String>               message;

    private static String                            packagesToScan;

    private static Map<String, ? extends Validation> validationRegister = new HashMap<>();

    public static Map<String, ? extends Validation> getValidationRegister() {
        return ValidateProperties.validationRegister;
    }

    public static void setValidationRegister(Map<String, ? extends Validation> validationRegister) {
        ValidateProperties.validationRegister = validationRegister;
    }

    public static Map<String, String> getMessage() {
        return ValidateProperties.message;
    }

    public static void setMessage(Map<String, String> message) {
        ValidateProperties.message = message;
    }

    public String getPackagesToScan() {
        return ValidateProperties.packagesToScan;
    }

    public static void setPackagesToScan(String packagesToScan) {
        ValidateProperties.packagesToScan = packagesToScan;
    }

}
