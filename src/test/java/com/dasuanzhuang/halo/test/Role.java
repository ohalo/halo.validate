package com.dasuanzhuang.halo.test;

import com.dasuanzhuang.halo.validate.annotation.Rule;
import com.dasuanzhuang.halo.validate.annotation.Validate;

public class Role {

    @Validate(rules = { 
            @Rule(value = "required:true", message = "id不能为空") ,
            @Rule(value = "between:[10,88]", message = "id必须在{0}到{1}之间")
    })
    private Integer id;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
