package com.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.common.Result;
import com.constants.CoreConstant;
import com.enums.ResultEnums;
import com.utils.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author huiyuan080
 * @date 2023/3/8
 * @apiNote 验证码的输出实现类
 */
@RestController
public class CaptchaController {

    @GetMapping("/captcha/getCaptchaBrowser")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        //获取base64编码值
        String imageBase64 = captcha.getImageBase64Data();
        captcha.write(outputStream);
    }



   @GetMapping(CoreConstant.CAPTCHA_URL)
   public Result getCaptcha(){
       HttpSession session = ServletUtils.getSession();
       CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(120, 40, 1, 0);
       session.setAttribute(CoreConstant.CAPTCHA_KEY,captcha);
       System.out.println("验证码---->"+captcha.getCode());
       return new Result(ResultEnums.SUCCESS.getCode(),"生成成功",captcha.getImageBase64Data());
   }


}
