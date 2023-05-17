package com.yunlong.seckilldemo.Valiator;


import com.yunlong.seckilldemo.vo.IsMobileValiator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValiator.class}   //添加实现类的.class文件
)
public @interface IsMobile {

    boolean required() default true;  //默认该号码为必须校验的

    String message() default "{手机号吗格式错误}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
