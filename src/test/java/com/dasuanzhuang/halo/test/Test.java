package com.dasuanzhuang.halo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dasuanzhuang.halo.validate.BaseValidateService;
import com.dasuanzhuang.halo.validate.IValidateService;
import com.dasuanzhuang.halo.validate.annotation.ValiObj;

public class Test {

    public String username(@ValiObj User user) {
        return user.getUsername();
    }

    public static void main(String[] args) {

        User user = new User();
        user.setUsername("admin1");
        user.setPassword("1231231");
        user.setAge(5);

        Role role = new Role();
        role.setId(10);
        user.setRole(role);

        List<Role> roles = new ArrayList<>();

        Role role1 = new Role();
        role1.setId(10);
        roles.add(role1);
        user.setRoles(roles);

        Map<String, Role> roleMap = new HashMap<>();

        Role role2 = new Role();
        role2.setId(15);
        roleMap.put("5", role2);
        user.setRoleMap(roleMap);
        // System.out.println(test.username(user));

        IValidateService validateService = new BaseValidateService();
        validateService.validate(user);
    }
}
