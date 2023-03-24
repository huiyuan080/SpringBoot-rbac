package com.aspectj;

import com.annotation.AppPermission;
import com.constants.CoreConstant;
import com.enums.ResultEnums;
import com.exceptions.BusinessException;
import com.utils.ServletUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/21
 * @apiNote 增强类
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class PermissionAspect {

    /**
     * 把使用这种注解的方法全部进行拦截
     *
     * @param appPermission
     */
    @Before(value = "@annotation(appPermission)")
    public void before(AppPermission appPermission) {
        //拿到当前的权限
        String permission = appPermission.value();
        //用户拥有的权限
        System.out.println("------判断权限用户是否有该权限-----");
        List<String> permissions = (List<String>) ServletUtils.getSession().getAttribute(CoreConstant.PERMISSIONS_KEY);
        if (CollectionUtils.isEmpty(permissions) || !(permissions.contains(permission))) {
            //如果用户权限为空 || 用户没有当前权限
            throw new BusinessException(ResultEnums.USER_NOT_ALLOW_ACCESS.getMsg());
        }

    }
}
