package com.dasuanzhuang.halo.validate.yaml;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * Created by zhaohuiliang on 2017/6/16.
 */
public class ValidateYaml {

    private static ValidateYaml validateYaml;

    private ValidateYaml() {
    }

    public static ValidateYaml getInstance() {
        if (validateYaml == null) {
            validateYaml = new ValidateYaml();
            validateYaml.load();
        }
        return validateYaml;
    }

    private Map<String, String> message;

    private String              packagesToScan;

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public String getPackagesToScan() {
        return packagesToScan;
    }

    public void setPackagesToScan(String packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @SuppressWarnings({ "unchecked", "unused" })
    private void load() {
        // 初始化Yaml解析器
        Yaml yaml = new Yaml();
        InputStream inputStream = ValidateYaml.class.getClassLoader().getResourceAsStream("halo.validate.yaml");
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
            validateYaml.setMessage(message);
            if (validateMap.get("packagesToScan") == null) return;
            validateYaml.setPackagesToScan((String) validateMap.get("packagesToScan"));
        }
    }

    @Override
    public String toString() {
        return "ValidateYaml{" + "message=" + message + ", packagesToScan='" + packagesToScan + '\'' + '}';
    }
}
