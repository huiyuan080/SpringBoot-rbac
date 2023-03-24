package com.interceptors;

import com.alibaba.fastjson.JSON;
import com.common.Result;
import com.constants.CoreConstant;
import com.domain.SysUser;
import com.enums.ResultEnums;
import com.exceptions.BusinessException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author huiyuan080
 * @date 2023/3/8
 * @apiNote
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 判断session中是否存在用户信息
         *  存在  放行 true
         *  不存在  返回 false
         */
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute(CoreConstant.SESSION_KEY);

        if (sysUser == null){
            //拦截方法  设置响应的数据的编码格式
            response.setContentType("application/json;charset=utf-8");
            request.setCharacterEncoding("utf-8");
            //获取输出字符流
            PrintWriter writer = response.getWriter();
            //将错误信息相应到浏览器
            writer.write(JSON.toJSONString(new Result<>(ResultEnums.ERROR, ResultEnums.USER_NOT_LOGIN)));
            writer.flush();
            writer.close();
            return false;
//            throw new BusinessException()
        }
        //放行方法
        return true;
    }
}
