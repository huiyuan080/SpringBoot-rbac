package com.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.crypto.digest.DigestUtil;
import com.async.AsyncSysLogInfoService;
import com.common.Result;
import com.constants.CoreConstant;
import com.domain.SysLoginInfo;
import com.domain.SysUser;
import com.enums.ResultEnums;
import com.enums.StateEnums;
import com.service.SysUserService;
import com.utils.HttpUtils;
import com.utils.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/8
 * @apiNote 登录和退出的处理类
 */
@RestController
public class LoginAndLogoutController extends BaseController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private AsyncSysLogInfoService asyncSysLogInfoService;

    @PostMapping("/login")
    public Result login(String username, String password, String code) {

        SysLoginInfo sysLoginInfo = new SysLoginInfo();
        SysUser sysUser;
        try {
            sysLoginInfo.setUserName(username);
            //获取使用的浏览器
            sysLoginInfo.setBrowser(HttpUtils.getBrowserName(ServletUtils.getRequest()));
            sysLoginInfo.setLoginTime(new Date());
            //设置操作系统
            sysLoginInfo.setOs(HttpUtils.getOsName(ServletUtils.getRequest()));
            sysLoginInfo.setIpAddr(HttpUtils.getIpAddr(ServletUtils.getRequest()));

            HttpSession session = ServletUtils.getSession();
            //取出session中的值
            CircleCaptcha captcha = (CircleCaptcha) session.getAttribute(CoreConstant.CAPTCHA_KEY);
            if (captcha == null) {
                //设置登录日志的登录信息  不同情况不同处理
                sysLoginInfo.setMsg("验证码不存在||验证码超时");
                sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());

                //没有获取验证码,或验证码超时
                return new Result(ResultEnums.ERROR, "验证码不存在||验证码超时");
            }
            if (!captcha.verify(code)) {
                sysLoginInfo.setMsg("验证码错误");
                sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());

                //验证码匹配错误
                return new Result(ResultEnums.ERROR, "验证码错误");
            }
            //验证码匹配正确后, 移除session里面存放的验证码信息
            session.removeAttribute(CoreConstant.CAPTCHA_KEY);
            //因为数据库中的密码进行了加密
            //所以要对密码加密  使用huTool工具对密码进行加密
            password = DigestUtil.md5Hex(password);
            //查询数据库中的用户信息
            sysUser = sysUserService.login(username, password);
            if (sysUser == null) {
                sysLoginInfo.setMsg("用户不存在||密码错误");
                sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());

                return new Result(ResultEnums.ERROR, "用户不存在||密码错误");
            }
            session.setAttribute(CoreConstant.SESSION_KEY, sysUser);
            sysLoginInfo.setMsg("登录成功");
            sysLoginInfo.setStatus(StateEnums.LOGIN_SUCCESS.getCode());

        } finally {
            //添加日志
            asyncSysLogInfoService.saveLoginLogInfo(sysLoginInfo);
        }
        return new Result("登录成功", sysUser);
    }


    @GetMapping("/logout")
    public Result logout() {
        HttpSession session = ServletUtils.getSession();
        //移除session里面的用户信息
        session.removeAttribute(CoreConstant.SESSION_KEY);
        //让session失效
        session.invalidate();
        return new Result("退出成功");
    }


    @GetMapping("/getUserInfo")
    public Result<Map<String, Object>> getUserInfo() {
        //获取当前登录用户信息
        SysUser sysUser = getSessionUser();
        sysUser.setPassword("********");
        List<String> permissions = sysUserService.queryUserPermissions(sysUser.getUserId());
        Map<String, Object> map = new HashMap<>(8);
        map.put("user", sysUser);
        map.put(CoreConstant.PERMISSIONS_KEY, permissions);
        ServletUtils.getSession().setAttribute(CoreConstant.PERMISSIONS_KEY, permissions);


        return new Result<>("操作成功", map);
    }

}
