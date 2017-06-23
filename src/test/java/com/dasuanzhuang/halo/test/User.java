package com.dasuanzhuang.halo.test;

import java.util.List;
import java.util.Map;

import com.dasuanzhuang.halo.validate.annotation.Rule;
import com.dasuanzhuang.halo.validate.annotation.Validate;

public class User {

    @Validate(rules = { 
            @Rule(value = "required:true"), 
            @Rule(value = "checkUsernameExists:", message = "用户名不能重复"), 
            @Rule(value = "maxlength:10"), 
            @Rule(value = "minlength:5")
    })
    private String username;
    
    @Validate(rules = { @Rule(value = "required:true", message = "密码不能为空") })
    private String password;

    @Validate(rules = {
            @Rule(value = "required:true", message = "年龄不能为空"),
            @Rule(value = "between:[5,88]", message = "年龄必须在{0}到{1}岁之间")
    })
    private  Integer age;
    
    @Validate(
            rules = {
                    @Rule(value = "required:true", message = "角色不能为空")
            },
            objClass  = Role.class
    )
    private Role role ;
    
    @Validate(
            rules = {
                    @Rule(value = "required:true", message = "角色list不能为空")
            },
            objClass  = Role.class
    )
    private List<Role> roles ; 
    
    @Validate(
            objClass  = Role.class
    )
    private Map<String,Role> roleMap;
    
    public Map<String, Role> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, Role> roleMap) {
        this.roleMap = roleMap;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}