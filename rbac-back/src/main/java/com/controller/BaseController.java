package com.controller;

import com.constants.CoreConstant;
import com.domain.SysUser;
import com.utils.ServletUtils;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * @author huiyuan080
 * @date 2023/3/8
 * @apiNote
 */
@Controller
public class BaseController {

    /**
     * 把获取用户信息这种经常调用的方法提出来
     *  封装到类中 增加代码的复用性 减少代码量
     *  需要使用的时候继承这个类，直接调用相关的方法
     * @return 返回用户信息
     */
    public SysUser getSessionUser(){
        HttpSession session = ServletUtils.getSession();
        return (SysUser) session.getAttribute(CoreConstant.SESSION_KEY);
    }

    public String getUserName(){
        return getSessionUser().getUserName();
    }

}
