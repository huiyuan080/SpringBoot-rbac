package com.controller;

import com.common.Page;
import com.common.Result;
import com.domain.SysDictData;
import com.service.SysDictDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huiyuan080
 * @apiNote
 */
@RestController
@RequestMapping("/sysDictData")
public class SysDictDataController extends BaseController {

    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 分页查询
     * @param page
     * @return
     */
    @PostMapping("getByPage")
    public Result getByPage(@RequestBody Page page) {
        return new Result(sysDictDataService.getByPage(page));
    }

    /**
     * 添加数据
     * @param sysDictData
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysDictData sysDictData) {
        sysDictData.setCreateBy(getUserName());
        sysDictData.setCreateTime(new Date());
        sysDictDataService.insertSelective(sysDictData);
        return new Result("添加成功");
    }

    /**
     * 更新数据
     * @param sysDictData
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysDictData sysDictData) {
        sysDictData.setUpdateBy(getUserName());
        sysDictData.setUpdateTime(new Date());
        sysDictDataService.updateByPrimaryKey(sysDictData);
        return new Result("修改成功");
    }

    /**
     * 根据id查询数据
     * @param dictCode
     * @return
     */
    @GetMapping("get/{dictCode}")
    public Result get(@PathVariable Long dictCode) {
        return new Result(sysDictDataService.selectByPrimaryKey(dictCode));
    }


    /**
     * 根据id删除数据
     * @param dictCode
     * @return
     */
    @DeleteMapping("delete/{dictCode}")
    public Result delete(@PathVariable Long dictCode) {
        sysDictDataService.deleteByPrimaryKey(dictCode);
        return new Result("删除成功");
    }


    /**
     * 根据类型查询数据
     * @param dictType
     * @return
     */
    @GetMapping("getDictDataByDictType/{dictType}")
    public Result getDictDataByDictType(@PathVariable String dictType){
        return new Result(sysDictDataService.getDictDataByDictType(dictType));
    }

}
