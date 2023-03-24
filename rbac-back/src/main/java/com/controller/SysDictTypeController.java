package com.controller;

import com.common.Page;
import com.common.Result;
import com.domain.SysDictType;
import com.enums.ResultEnums;
import com.service.SysDictTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huiyuan080
 * @date 2023/3/8
 * @apiNote 字典管理模块
 */
@RestController
@RequestMapping("/sysDictType")
public class SysDictTypeController extends BaseController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 分页查询字典类型数据
     * 前端传参
     * {
     * "pageNumber":1
     * "pageSize":10,
     * "params":{
     * "dictName":"",  // 字典类型名称
     * "dictType":""  // 字典类型
     * "status":0|1    字典类型状态
     * }
     * }
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result getSysDictTypeByPage(@RequestBody Page page) {
        return new Result(sysDictTypeService.getSysDictTypeByPage(page));
    }

    /**
     * 添加字典类型数据
     *
     * @param sysDictType
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysDictType sysDictType) {
        //设置添加的用户名和添加时间
        sysDictType.setCreateTime(new Date());
        sysDictType.setCreateBy(getUserName());
        sysDictTypeService.insert(sysDictType);
        return new Result("添加成功");
    }

    /**
     * 修改字典类型
     *
     * @param sysDictType
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysDictType sysDictType) {
        //设置修改的用户名和修改时间
        sysDictType.setUpdateTime(new Date());
        sysDictType.setUpdateBy(getUserName());
        sysDictTypeService.updateByPrimaryKey(sysDictType);
        return new Result("更新成功");
    }

    /**
     * 删除字典类型数据 根据路径id
     *
     * @param dictId
     * @return
     */
    @DeleteMapping("/delete/{dictId}")
    public Result delete(@PathVariable Long dictId) {
        sysDictTypeService.deleteByPrimaryKey(dictId);
        return new Result("删除成功");
    }

    /**
     * 根据dictId查询对应的字典类型
     *
     * @param dictId
     * @return
     */
    @GetMapping("/get/{dictId}")
    public Result get(@PathVariable Long dictId) {
        return new Result("操作成功", sysDictTypeService.selectByPrimaryKey(dictId));
    }

    /**
     * 获取全部的字典类型数据(不分页)
     *
     * @return
     */
    @GetMapping("/getAllDictType")
    public Result getAllDictType() {
        return new Result(sysDictTypeService.getAllDictType( ));
    }


    @GetMapping("/refreshCache")
    public Result refreshCache() {
        /*
         *   引入缓存
         *     字典数据 变化频率较低  每次获取字典数据 没有必要到数据库来查询  可以使用缓存来缓存字典数据
         *        如果客户端需要某个字典类型数据 后端提供一个接口 接收字典类型值 根据字典类型值到缓存查找即可
         *   刷新缓存   实际上就是将数据库字典数据查出来放入缓存
         */
        sysDictTypeService.refreshCache();
        return new Result("缓存同步成功");
    }



}
