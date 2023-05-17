package com.yunlong.seckilldemo.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;


@Component
public class MD5Util {
    //md5加密  这直接就是加密的实体 过程
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    //加密的盐值
    private static final  String salt="9d5b364d";

    //输入密码到后端密码的加密 输入的密码作为参数  第一次加密
    public static String inputPassToFromPass(String inputPass){
        String str="" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //第二次加密 后端到数据库的加密  随机生成salt 第二次的盐值随机生成
    public static String FromPassToDBPass(String FromPass, String salt){
        String str=salt.charAt(0)+salt.charAt(3)+FromPass+salt.charAt(4)+salt.charAt(5);
        return md5(str);
    }

    //两次加密只要调用这一个方法就行
    public  static String inputPassToDBPass(String inputPass,String salt){
        String fromPass=inputPassToFromPass(inputPass);
        String DBPass=FromPassToDBPass(fromPass,salt);
        return DBPass;
    }

    //md5加密测试
    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456")); //ce21b747de5af71ab5c2e20ff0a60eea  第一次加密的值
        System.out.println(FromPassToDBPass(inputPassToFromPass("123456"),"1a2b3c4d")); //af3765185124e3e950432fea27922af6 第二次加密的值
        System.out.println(inputPassToDBPass("123456","1a2b3c4d")); //直接一个方法加密的值：af3765185124e3e950432fea27922af6
    }


}
