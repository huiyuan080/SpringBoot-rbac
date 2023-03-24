package com.annotation;

import java.lang.annotation.*;

/**
 * @author huiyuan080
 * @date 2023/3/21
 * @apiNote
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AppPermission {
    //配置权限标识符
    String value();
}
