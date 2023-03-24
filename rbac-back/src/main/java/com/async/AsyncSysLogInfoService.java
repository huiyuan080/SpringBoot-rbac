package com.async;

import com.domain.SysLoginInfo;
import com.service.SysLoginInfoService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huiyuan080
 * @date 2023/3/22
 * @apiNote 异步业务处理类
 */
@Component
@Async
@EnableAsync
public class AsyncSysLogInfoService {

    @Resource
    private SysLoginInfoService sysLoginInfoService;

    /**
     * 添加登陆日志
     */
    public void saveLoginLogInfo(SysLoginInfo loginInfo) {
        String ipAddr = loginInfo.getIpAddr();
        //处理IP
        if (ipAddr != null) {
            if ("127.0.0.1".equalsIgnoreCase(ipAddr) || "localhost".equalsIgnoreCase(ipAddr)) {
                loginInfo.setLoginLocation("本地");
            } else {
                //大家可以去网上找对应的IP查询API，去发请求得到IP的所在地址
            }
        }
        this.sysLoginInfoService.insertSelective(loginInfo);
    }

}
