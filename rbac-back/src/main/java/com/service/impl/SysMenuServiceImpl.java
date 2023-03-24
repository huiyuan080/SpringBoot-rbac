package com.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.constants.CoreConstant;
import com.domain.SysUser;
import com.enums.StateEnums;
import com.vo.RouterVo;
import com.vo.SysMenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.domain.SysMenu;
import com.mapper.SysMenuMapper;
import com.service.SysMenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/13
 * @apiNote
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public int deleteByPrimaryKey(Long menuId) {
        return sysMenuMapper.deleteByPrimaryKey(menuId);
    }

    @Override
    public int insert(SysMenu record) {
        return sysMenuMapper.insert(record);
    }

    @Override
    public int insertSelective(SysMenu record) {
        /*
         * 获取上级菜单类型 &上级菜单id parentId
         * */
        Integer menuType = record.getMenuType();
        Long parentId = record.getParentId();
        int result = 0;
        /*
         * 开始自旋锁
         * */
        while (true) {
            try {
                Long maxId = sysMenuMapper.queryMaxIdByMenuTypeAndParentId(menuType, parentId);
                if (maxId != null) {
                    //非空 主键加1 即可
                    record.setMenuId(maxId + 1L);
                } else {
                    if (menuType.equals(StateEnums.MENU_M.getCode())) {
                        //当前为一级菜单
                        record.setMenuId(1L);
                    } else if (menuType.equals(StateEnums.MENU_C.getCode())) {
                        //当前为二级菜单
                        record.setMenuId(Long.parseLong(parentId + "01"));
                    } else if (menuType.equals(StateEnums.MENU_F.getCode())) {
                        record.setMenuId(Long.parseLong(parentId + "01"));
                    }
                }
                result = sysMenuMapper.insertSelective(record);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return result;
    }

    @Override
    public SysMenu selectByPrimaryKey(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public int updateByPrimaryKey(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysMenu> listMenuMC(SysUser sessionUser) {
        List<SysMenu> result = null;
        if (sessionUser.getUserType().equals(StateEnums.USER_TYPE_ADMIN.getCode())) {
            result = sysMenuMapper.queryAllMenuMC();
        } else {
            result = sysMenuMapper.queryAllMenuMCByUserId(sessionUser.getUserId());
        }
        return result;
    }

    @Override
    public Long countChildrenByParentId(Long menuId) {
        return sysMenuMapper.countChildrenByParentId(menuId);

    }

    @Override
    public List<SysMenu> listMenu(SysMenu sysMenu, SysUser sessionUser) {
        /*
         * 如果用胡为超级管理员就查询全部
         *  如果用户为普通用户 就查询相对应的权限
         */
        List<SysMenu> sysMenus = null;
        if (sessionUser.getUserType().equals(StateEnums.USER_TYPE_ADMIN.getCode())) {
            //查询所有记录
            sysMenus = sysMenuMapper.listMenu(sysMenu);
        } else {
            //查询普通记录
            sysMenus = sysMenuMapper.listMenuByUserId(sysMenu, sessionUser.getUserId());
        }


        return sysMenus;
    }

    @Override
    public List<RouterVo> getRouters(SysUser sessionUser) {
        /**
         * 1.判断用户类型
         *    如果管理员 查询所有菜单记录
         *    如果普通用户 根据用户扮演的角色查询指定菜单记录
         *  结果   List<SysMenu>
         * 2.根据List<SysMenu>  ---->构建 List<SysMenuVo>
         *      先构建一级菜单(目录)  topMenuList =   系统管理  日志管理
         *      根据顶级菜单获取二级菜单  children
         * 3.根据第二步结果 构建 List<RouterVo>
         */

        //转换数据  把查询到的SysMenu对象封装到一个新的对象里面去
        List<SysMenu> sysMenus = this.listMenuMC(sessionUser);
        List<SysMenuVo> sysMenuVos = new ArrayList<>();
        sysMenus.forEach(sysMenu -> {
            SysMenuVo sysMenuVo = new SysMenuVo();
            BeanUtils.copyProperties(sysMenu, sysMenuVo);
            sysMenuVos.add(sysMenuVo);
        });
        //从转换的数据中获取顶级菜单
        List<SysMenuVo> topMenuList = new ArrayList<>();
        sysMenuVos.forEach(sysMenuVo -> {
            //遍历每一个数据  如果数据的是目录 就添加到菜单集合
            if (sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())) {
                topMenuList.add(sysMenuVo);
            }
        });

        //获取顶级菜单下的每一个子菜单
        sysMenuVos.forEach(sysMenuVo -> {
            topMenuList.forEach(topMenuVo -> {
                //如果查询到数据的父Id是菜单的id  那么就确定这个数据是菜单的一个子类
                //就把这个数据添加到菜单的子类里面去
                if (sysMenuVo.getParentId().equals(topMenuVo.getMenuId())) {
                    topMenuVo.getChildren().add(sysMenuVo);
                }
            });
        });
        return buildRouterVoList(topMenuList);
    }

    private List<RouterVo> buildRouterVoList(List<SysMenuVo> topMenuVoList) {
        List<RouterVo> result = new ArrayList<>();
        //遍历我们拿到的数据  封装好每一项 最后返回
        for (SysMenuVo sysMenuVo : topMenuVoList) {
            RouterVo routerVo=new RouterVo();
            routerVo.setMeta(new RouterVo.MetaVo(sysMenuVo.getMenuName(),sysMenuVo.getIcon()));
            routerVo.setAlwaysShow(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())?true:false);
            routerVo.setComponent(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())? CoreConstant.DEFAULT_COMPONENT:sysMenuVo.getComponent());
            routerVo.setHidden(sysMenuVo.getVisible()==0?false:true);
            routerVo.setName(sysMenuVo.getMenuName());
            routerVo.setPath(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())?CoreConstant.URL_SPLIT+sysMenuVo.getPath():sysMenuVo.getPath());
            routerVo.setRedirect(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())?CoreConstant.NO_REDIRECT:null);
            List<SysMenuVo> children= sysMenuVo.getChildren();
            if(CollectionUtil.isNotEmpty(children)){
                routerVo.setChildren(buildRouterVoList(children));
            }
            result.add(routerVo);

        }
        return result;
    }
}










