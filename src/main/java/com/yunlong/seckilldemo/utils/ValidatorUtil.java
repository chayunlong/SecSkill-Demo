package com.yunlong.seckilldemo.utils;

//验证手机号是否有效 正则表达式



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern mobile_pattern=Pattern.compile("[1]([3-9]\\d{9})");  //手机号的模式 正则表达式

    public static boolean isValidPhoneNumber(String mobile){
        if(mobile.isEmpty()){
            return false;
        }

        Matcher matcher=mobile_pattern.matcher(mobile);
        //System.out.println("号码已经验证");
        return matcher.matches(); //是否匹配成功
    }

}
