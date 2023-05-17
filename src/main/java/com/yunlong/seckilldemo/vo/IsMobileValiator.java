package com.yunlong.seckilldemo.vo;

import com.yunlong.seckilldemo.Valiator.IsMobile;
import com.yunlong.seckilldemo.utils.ValidatorUtil;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


//手机号码校验规则
public class IsMobileValiator implements ConstraintValidator<IsMobile,String> {
    private boolean required =false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isValidPhoneNumber(value);
        }else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return ValidatorUtil.isValidPhoneNumber(value);
            }
        }
    }


}
