package com.controller;

import com.common.Page;
import com.common.Result;
import com.domain.SysRole;
import com.enums.StateEnums;
import com.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author huiyuan080
 * @apiNote
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @PostMapping("getByPage")
    public Result getByPage(@RequestBody Page page) {
        page = sysRoleService.getByPage(page);
        return new Result(page);
    }

    /**
     * 添加数据
     *
     * @param sysRole
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysRole sysRole) {
        sysRole.setCreateBy(getUserName());
        sysRole.setCreateTime(new Date());
        sysRoleService.insertSelective(sysRole);
        return new Result("添加成功");
    }

    /**
     * 更新数据
     *
     * @param sysRole
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysRole sysRole) {
        sysRole.setUpdateBy(getUserName());
        sysRole.setUpdateTime(new Date());
        sysRoleService.updateByPrimaryKeySelective(sysRole);
        return new Result("修改成功");
    }

    /**
     * 根据id查询数据
     *
     * @param roleId
     * @return
     */
    @GetMapping("get/{roleId}")
    public Result get(@PathVariable Long roleId) {
        return new Result(sysRoleService.selectByPrimaryKey(roleId));
    }


    /**
     * 根据id删除数据
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("delete/{roleId}")
    public Result delete(@PathVariable Long roleId) {
        SysRole sysRole = sysRoleService.selectByPrimaryKey(roleId);
        sysRole.setDelFlag(StateEnums.DELETED.getCode());
        sysRoleService.updateByPrimaryKeySelective(sysRole);
        return new Result("删除成功");
    }

    /**
     * 保存角色菜单之间的关系
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("saveRoleMenu")
    public Result saveRoleMenu(Long roleId, Long[] menuIds) {
        sysRoleService.saveRoleMenu(roleId, menuIds);
        return new Result("保存成功");
    }

    @GetMapping("getCurrentRoleHasMenuIdsByRoleId/{roleId}")
    public Result getCurrentRoleHasMenuIdsByRoleId(@PathVariable Long roleId) {
        List menuId = sysRoleService.getCurrentRoleHasMenuIdsByRoleId(roleId);
        return new Result("记录查询成功", menuId);
    }


    /**
     * 查询所有角色不分页
     *
     * @return
     */
    @GetMapping("getAllRoles")
    public Result getAllRoles() {
        List<SysRole> sysRoles = sysRoleService.getAllRoles();
        return new Result(sysRoles);
    }

    /**
     * 保存用户和角色之间的关系
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping("saveUserRoles")
    public Result saveUserRoles(Long userId, Long[] roleIds) {
        sysRoleService.saveUserRoles(userId, roleIds);
        return new Result("用户角色分配成功");
    }


    /**
     * 根据用户ID查询用户已拥有的角色IDS
     *
     * @param userId
     * @return
     */
    @GetMapping("getRoleIdsByUserId/{userId}")
    public Result getRoleIdsByUserId(@PathVariable Long userId) {
       List<Long> roleIds =  sysRoleService.getRoleIdsByUserId(userId);
        return new Result(roleIds);
    }

}
