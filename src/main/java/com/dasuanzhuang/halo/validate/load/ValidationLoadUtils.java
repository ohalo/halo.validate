package com.dasuanzhuang.halo.validate.load;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.yaml.snakeyaml.Yaml;

import com.dasuanzhuang.halo.validate.annotation.ValidationRegister;
import com.dasuanzhuang.halo.validate.api.Validation;

public class ValidationLoadUtils {

    private static Map<String, Validation> validationRegister = new HashMap<>();
    private static List<String>            classArray         = new ArrayList<>();

    @SuppressWarnings({ "unchecked", "unused" })
    private void loadXml(String validateYaml) {
        // 初始化Yaml解析器
        Yaml yaml = new Yaml();
        InputStream inputStream = ValidationLoadUtils.class.getClassLoader().getResourceAsStream(validateYaml);
        // 读入文件
        Object result = yaml.load(inputStream);
        if (result instanceof Map) {
            if (result == null) return;
            Map<String, Object> yamlParams = (Map<String, Object>) result;
            if (yamlParams == null) return;
            if (yamlParams.get("validate") == null) return;
            Map<String, Object> validateMap = (Map<String, Object>) yamlParams.get("validate");
            if (validateMap.get("message") == null) return;
            Map<String, String> message = (Map<String, String>) validateMap.get("message");
            ValidateProperties.setMessage(message);
            if (validateMap.get("packagesToScan") == null) return;
            ValidateProperties.setPackagesToScan((String) validateMap.get("packagesToScan"));
        }
    }

    @SuppressWarnings("unchecked")
    public static void load(String packagesToScan) throws UnsupportedEncodingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String path = packagesToScan.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url != null) {
            String filePath = URLDecoder.decode(url.getFile(), "utf-8");
            File dir = new File(filePath);
            listFiles(dir);
            for (String filePackage : classArray) {
                Class<Validation> clazz = (Class<Validation>) Class.forName(filePackage);
                ValidationRegister vr = clazz.getAnnotation(ValidationRegister.class);
                if (vr != null) {
                    String key = vr.value();
                    validationRegister.put(key, clazz.newInstance());
                }
            }
        }

        ValidateProperties.setValidationRegister(validationRegister);
    }

    private static void listFiles(File file) {
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
}
