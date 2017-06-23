#2017年6月16日

#halo.validate

    @Validate(rules = { 
            @Rule(value = "required:true", message = "用户名不能为空"), 
            @Rule(value = "checkUsernameExists:", message = "用户名不能重复"), 
            @Rule(value = "maxlength:10", message = "输入长度最多是 10 的字符串（汉字算一个字符）。"), 
            @Rule(value = "minlength:5", message = "输入长度最小是 5 的字符串（汉字算一个字符）。")
    })
    private String username;
    
    @Validate(rules = { @Rule(value = "required:true", message = "密码不能为空") })
    private String password;

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("admin");
        IValidateService validate = new BaseValidateService();
        validate.validate(user);
    }
    
    
    
    
jquery.Validate 形式的验证方式
继承Validation 实现验证
配置ValidationRegister 注解

#例子：

###校验器
	@ValidationRegister("checkUsernameExists")
	public class UsernameValidation implements Validation {
	
	    @Override
	    public Boolean validate(Object attrValue, Class<?> attrType, Object params) {
	        if (attrValue.equals("admin")) { return true; }
	        return false;
	    }
	}


###配置
	加入validate 配置（halo.validate.yaml 默认放于/src/main/resources）： message 提示信息， packagesToScan是扫描注册的验证类
	
	validate:
		packagesToScan: com.dasuanzhuang.halo.test	
	    message:
	      required: 不能为空
	      maxlength: 长度不能超过{0}
	      minlength: 长度不能小于{0}	


###应用：
	
    @Validate(rules = { 
            @Rule(value = "required:true", message = "用户名不能为空"), 
            @Rule(value = "checkUsernameExists:", message = "用户名不能重复")
    })
    private String username;
    
###验证
	public static void main(String[] args) {
        User user = new User();
        user.setUsername("admin");
        IValidateService validate = new BaseValidateService();
        validate.validate(user);
    }
    
###出错提示：
	Exception in thread "main" com.dasuanzhuang.halo.validate.error.ValidateException: [User.username(java.lang.String)]
		at com.dasuanzhuang.halo.validate.BaseValidateService.validate(BaseValidateService.java:115)
		at com.dasuanzhuang.halo.test.Test.main(Test.java:45)
	Caused by: com.dasuanzhuang.halo.validate.error.ValidateException: [rule:{checkUsernameExists:},message:用户名不能重复]
		at com.dasuanzhuang.halo.validate.BaseValidateService.parseRuleAndValidate(BaseValidateService.java:60)
		at com.dasuanzhuang.halo.validate.BaseValidateService.validate(BaseValidateService.java:83)
		... 1 more

	    
以上是halo.validate的全部配置，配置packagesToScan标签可以扫描ValidationRegister注解的类
	    
    
#@Validate 验证注解：
	可以注释到属性上 ,验证的时候会获取这个注解
	rules，规则列表,可以存在多个验证规则
	objClass：如果对象内存在验证对象，可以添加objClass = Role.class 标识当前对象需要验证，如果不加，只验证规则验证
	
#@Rule 是验证规则：
	你可以在value中填写验证规则如: required:true  ,必须以：分割 左边代表校验器标识，右边标识传递的参数，没有参数可以为空，多余一个值用[1,2,3]类似数组的表现形式：验证注解中可以存在多个规则。

#校验器标识
	
	系统默认的校验器包括required，maxlength，minlength，也可以自定义校验器，以下校验器标识为checkUsernameExists

	@ValidationRegister("checkUsernameExists")
	public class UsernameValidation implements Validation {
	
	    @Override
	    public Boolean validate(Object attrValue, Class<?> attrType, Object params) {
	        if (attrValue.equals("admin")) { return true; }
	        return false;
	    }
	}
	
#自定义校验器：

	继承Validation 并且 需要配置注解ValidationRegister，实现validate(Object attrValue, Class<?> attrType, Object params) 方法，attrValue 传入属性值，attrType属性类型，params 冒号后面的参数，如果是多个值，以String[] 形式传递
	
	required，maxlength，minlength 使用说明：
	required:true  对象不为空，如果是String类，默认会验证是否为空字符串
	maxlength:10   表示长度不能超过10
	minlength:10   表示长度最小不能小于10

 
#支持内部对象验证

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
            rules = {
                    @Rule(value = "required:true", message = "角色map不能为空")
            },
            objClass  = Role.class
    )
    private Map<String,Role> roleMap;
    
role是嵌套对象，在@Validate 中指定objClass  = Role.class，就可以完成嵌套对象验证

	 @Validate(rules = { 
            @Rule(value = "required:true", message = "用户名不能为空"), 
            @Rule(value = "checkUsernameExists:", message = "用户名不能重复"), 
            @Rule(value = "maxlength:10", message = "输入长度最多是 10 的字符串（汉字算一个字符）。"), 
            @Rule(value = "minlength:5", message = "输入长度最小是 5 的字符串（汉字算一个字符）。")
    }, objClass  = Role.class)
    

#与spring 集成
	ValidationBuilder.createValidation  根据名字返回Validation对象  ，建议重新定义ValidationBuilder.createValidation方法