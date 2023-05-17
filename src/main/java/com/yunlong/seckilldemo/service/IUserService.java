package com.yunlong.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunlong.seckilldemo.pojo.User;
import com.yunlong.seckilldemo.vo.LoginVo;
import com.yunlong.seckilldemo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yunlong
 * @since 2023-03-19
 */

//登录
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request,HttpServletResponse response);

    User getUserByTicket(String ticket,HttpServletRequest request,HttpServletResponse response);
    //更新密码
    RespBean updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);

}
