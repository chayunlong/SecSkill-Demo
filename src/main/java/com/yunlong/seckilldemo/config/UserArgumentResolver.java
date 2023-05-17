package com.yunlong.seckilldemo.config;

import com.yunlong.seckilldemo.pojo.User;
import com.yunlong.seckilldemo.service.IUserService;
import com.yunlong.seckilldemo.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component  //添加这个注解之后就可以注入到其weConfig类中
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService iUserService;


    //这个方法返回true之后才会执行下面的方法
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();  //获取传入参数的类型
        return clazz == User.class;   //只有当传入参数的类型为user时 才会返回true 才会执行下面的逻辑
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String ticket = CookieUtil.getCookieValue(request, "userTicket", false);
        if(StringUtils.isEmpty(ticket)){
            return null;
        }

        return iUserService.getUserByTicket(ticket, request, response);
    }


}
