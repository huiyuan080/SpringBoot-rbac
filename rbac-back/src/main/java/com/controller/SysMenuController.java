package com.controller;

import com.common.Result;
import com.domain.SysDept;
import com.domain.SysMenu;
import com.enums.ResultEnums;
import com.service.SysMenuService;
import com.vo.RouterVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/13
 * @apiNote
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController {

    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("/listMenu")
    public Result listMenu(SysMenu sysMenu) {
        return new Result("查询成功", sysMenuService.listMenu(sysMenu, getSessionUser()));
    }

    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenu.setCreateBy(getUserName());
        sysMenu.setCreateTime(new Date());
        sysMenuService.insertSelective(sysMenu);
        return new Result("添加成功");
    }

    @PutMapping("update")
    public Result update(@RequestBody SysMenu sysMenu) {
        sysMenu.setUpdateBy(getUserName());
        sysMenu.setUpdateTime(new Date());
        sysMenuService.updateByPrimaryKeySelective(sysMenu);
        return new Result("更新成功");
    }

    @DeleteMapping("/delete/{menuId}")
    public Result delete(@PathVariable Long menuId) {
        Long count = sysMenuService.countChildrenByParentId(menuId);
        if (count > 0) {
            return new Result(ResultEnums.ERROR.getCode(),"当前菜单存在子菜单,无法删除");
        }
        sysMenuService.deleteByPrimaryKey(menuId);
        return new Result("删除成功");
    }

    @GetMapping("/get/{menuId}")
    public Result get(@PathVariable Long menuId) {
        return new Result("查询成功", sysMenuService.selectByPrimaryKey(menuId));
    }

    /**
     *只查询菜单和目录
     */
    @GetMapping("listMenuMC")
    public Result<List<SysMenu>> listMenuMC() {
        List<SysMenu> sysMenuList = sysMenuService.listMenuMC(getSessionUser());
        return new Result("记录查询成功",sysMenuList);
    }


    @GetMapping("getRouters")
    public Result getRouters(){
       List<RouterVo> s= sysMenuService.getRouters(getSessionUser());
        return new Result(s);
    }


}













