package com.yunlong.seckilldemo.utils;

import java.util.UUID;

public class UUIDUtil {
    public static String uuid(){
        //生成uuid 然后把生成的uuid 的“-” 替换掉
        return UUID.randomUUID().toString().replace("-","");
    }
}
