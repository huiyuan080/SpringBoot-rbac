package com.test;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.domain.SysUser;
import com.mapper.SysUserMapper;
import com.service.SysUserService;
import com.service.impl.SysUserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/7
 * @apiNote
 */
public class CodeTest {

    public static void main(String[] args) {
       /*ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        SysUserMapper bean = context.getBean(SysUserMapper.class);
        SysUser sysUser = bean.selectByPrimaryKey( 2L);
        System.out.println(sysUser);*/

        /*//ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        String imageBase64 = captcha.getImageBase64();
        System.out.println(imageBase64);*/
        Map<String,Object> map = new HashMap<>(15);
        System.out.println(map.size());
    }
}
