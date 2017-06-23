package com.dasuanzhuang.halo.validate.annotation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dasuanzhuang.halo.validate.yaml.ValidateYaml;
import org.apache.commons.lang.StringUtils;

import com.dasuanzhuang.halo.validate.api.impl.MaxLengthValidation;
import com.dasuanzhuang.halo.validate.api.impl.MinLengthValidation;
import com.dasuanzhuang.halo.validate.api.impl.RequiredValidation;

public class ValidationRegisterScan {

    private static Map<String, Class<?>> validationRegister = new HashMap<>();
    private static List<String>          classArray         = new ArrayList<>();

    static {
        validationRegister.put("required", RequiredValidation.class);
        validationRegister.put("maxlength", MaxLengthValidation.class);
        validationRegister.put("minlength", MinLengthValidation.class);
        try {
            ValidateYaml validateYaml = ValidateYaml.getInstance();
            load(validateYaml.getPackagesToScan());
        } catch (UnsupportedEncodingException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ValidationRegisterScan.load("com.dasuanzhuang.halo.test");
        } catch (UnsupportedEncodingException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void load(String packagesToScan) throws UnsupportedEncodingException, ClassNotFoundException {
        String path = packagesToScan.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url != null) {
            String filePath = URLDecoder.decode(url.getFile(), "utf-8");
            File dir = new File(filePath);
            listFiles(dir);
            for (String filePackage : classArray) {
                Class<?> clazz = Class.forName(filePackage);
                ValidationRegister vr = clazz.getAnnotation(ValidationRegister.class);
                if (vr != null) {
                    String key = vr.value();
                    validationRegister.put(key, clazz);
                }
            }
        }
    }

    public static void listFiles(File file) {
        if (file == null) { return; }
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                listFiles(f);
            } else {
                String fileName = f.getAbsolutePath();
                if (StringUtils.endsWith(fileName, ".class")) {
                    String nosuffixFileName = fileName.substring(8 + fileName.lastIndexOf("classes"), fileName.indexOf(".class"));
                    String filePackage = nosuffixFileName.replaceAll("\\\\", ".");
                    classArray.add(filePackage);
                }
            }
        }
    }

    public static Class<?> get(String key) {
        return validationRegister.get(key);
    }
}
