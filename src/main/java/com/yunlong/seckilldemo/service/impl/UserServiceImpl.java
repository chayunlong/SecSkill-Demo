package com.yunlong.seckilldemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunlong.seckilldemo.exception.GlobalException;
import com.yunlong.seckilldemo.mapper.UserMapper;
import com.yunlong.seckilldemo.pojo.User;
import com.yunlong.seckilldemo.service.IUserService;
import com.yunlong.seckilldemo.utils.CookieUtil;
import com.yunlong.seckilldemo.utils.MD5Util;
import com.yunlong.seckilldemo.utils.UUIDUtil;
import com.yunlong.seckilldemo.utils.ValidatorUtil;
import com.yunlong.seckilldemo.vo.LoginVo;
import com.yunlong.seckilldemo.vo.RespBean;
import com.yunlong.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yunlong
 * @since 2023-03-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    //注入redis模板
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    //登录
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
/*  有了自定义校验注解之后不需要在这进行校验
        //if(mobile.isEmpty()&password.isEmpty()){   //判断字符串是否为空
        //判断字符串是否为空即是否为 null 值或者其长度是否为零。
        if(StringUtils.isEmpty(mobile)&&StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //参数校验 判断是否为手机号
        if(!ValidatorUtil.isValidPhoneNumber(mobile)){
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }

 */
        //根据手机号查询用户
        User user = userMapper.selectById(mobile);
        System.out.println(user);
        if(user==null){
            // return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);  //抛出登录错误的异常
        }
        //判断密码是否正确
        if(!MD5Util.FromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){

            //return RespBean.error(RespBeanEnum.PASSWORD_ERROR);
            throw new GlobalException(RespBeanEnum.PASSWORD_ERROR);  //抛出密码错误的异常
        }
//        System.out.println(user.getNickname());
//        System.out.println("登录成功");
        //登录成功后 创建一个cookie来存储user对象
        String ticket = UUIDUtil.uuid(); //随机产生一个名字id
//        request.getSession().setAttribute(ticket,user);  //获取一个会话 将user存储到里面
        //将用户直接存储到redis中
        System.out.println(user);
        redisTemplate.opsForValue().set("user:"+ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket,-1,false); //设置一个cookie
        return RespBean.success();

    };

    public User getUserByTicket(String ticket,HttpServletRequest request,HttpServletResponse response){
        if(StringUtils.isEmpty(ticket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        //System.out.println(user);
        if(user!=null){
            CookieUtil.setCookie(request,response,"userTicket",ticket,-1,false);
        }



        return user;
    }


    //更新密码
    @Override
    public RespBean updatePassword(String userTicket, String password,HttpServletRequest request,HttpServletResponse response) {
        //调用上面的方法获取对象
        User user=getUserByTicket(userTicket,request,response);
        if(user==null){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password,user.getSalt()));
        int result = userMapper.updateById(user);
        if(result==1){
            //
            redisTemplate.delete("user:"+userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }

}
