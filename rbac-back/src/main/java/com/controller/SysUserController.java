package com.controller;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.db.DbUtil;
import com.annotation.AppPermission;
import com.annotation.Log;
import com.common.Page;
import com.common.Result;
import com.constants.CoreConstant;
import com.domain.SysRole;
import com.domain.SysUser;
import com.enums.BusinessType;
import com.enums.ResultEnums;
import com.enums.StateEnums;
import com.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/8
 * @apiNote
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @AppPermission(value = "system:user:query")
    @PostMapping("getByPage")
    @Log(title="用户管理",businessType = BusinessType.OTHER)
    public Result getByPage(@RequestBody Page page) {
        page = sysUserService.getByPage(page);
        return new Result(page);
    }

    @Resource
    private SysUserService sysUserService;

    /**
     * 添加数据
     *
     * @param sysUser
     * @return
     */

    @PostMapping("save")
    @AppPermission(value = "system:user:add")
    @Log(title="用户管理",businessType = BusinessType.INSERT)

    public Result save(@RequestBody SysUser sysUser) {
        /*
         *用户名 唯一
         */
        SysUser tempUser = sysUserService.selectByUserName(sysUser.getUserName());
        if (tempUser != null) {
            return new Result(ResultEnums.ERROR.getCode(), "用户已经存在");
        }
        sysUser.setAvatar("http://rrvco8nz5.hd-bkt.clouddn.com/logo.gif");
        sysUser.setCreateBy(getUserName());
        sysUser.setCreateTime(new Date());
        sysUser.setPassword(DigestUtil.md5Hex(CoreConstant.DEFAULT_PASSWORD));
        sysUser.setDelFlag(StateEnums.NOT_DELETED.getCode());
        sysUser.setUserType(StateEnums.USER_TYPE_NORMAL.getCode());
        sysUserService.insertSelective(sysUser);
        return new Result("添加成功");
    }

    /**
     * 更新数据
     *
     * @param sysUser
     * @return
     */
    @PutMapping("update")
    @AppPermission(value = "system:user:update")
    @Log(title="用户管理",businessType = BusinessType.UPDATE)

    public Result update(@RequestBody SysUser sysUser) {
        SysUser tempUser = sysUserService.selectByUserName(sysUser.getUserName());
        if (null != tempUser && !(tempUser.getUserId().equals(sysUser.getUserId()))) {
            return new Result(ResultEnums.ERROR.getCode(), "用户已经存在");
        }
        sysUser.setUpdateBy(getUserName());
        sysUser.setUpdateTime(new Date());
        sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result("修改成功");
    }

    /**
     * 根据id删除数据
     *
     * @param userId
     * @return
     */
    @DeleteMapping("delete/{userId}")
    @AppPermission(value = "system:user:remove")
    @Log(title="用户管理",businessType = BusinessType.DELETE)

    public Result delete(@PathVariable Long userId) {
        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        sysUser.setDelFlag(StateEnums.DELETED.getCode());
        sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result("删除成功");
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @GetMapping("resetPwd/{userId}")
    public Result resetPwd(@PathVariable Long userId) {
        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        sysUser.setPassword(CoreConstant.DEFAULT_PASSWORD);
        sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result("修改成功");
    }


    /**
     * 根据id查询用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/get/{userId}")
    public Result getUserByUserId(@PathVariable long userId) {
        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        return new Result(ResultEnums.SUCCESS.getMsg(), sysUser);
    }


}
