package com.controller;

import com.common.Result;
import com.domain.SysDept;
import com.enums.ResultEnums;
import com.service.SysDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huiyuan080
 * @date 2023/3/10
 * @apiNote
 */
@RestController
@RequestMapping("/sysDept")
public class SysDeptController extends BaseController {

    @Resource
    private SysDeptService sysDeptService;


    @GetMapping("listDept")
    public Result listDept(SysDept sysDept) {
        return new Result(sysDeptService.listDept(sysDept));
    }

    @PostMapping("save")
    public Result save(@RequestBody SysDept sysDept) {
        sysDept.setCreateBy(getUserName());
        sysDept.setCreateTime(new Date());
        sysDeptService.insertSelective(sysDept);
        return new Result("添加成功");
    }

    @PutMapping("update")
    public Result update(@RequestBody SysDept sysDept) {
        sysDept.setUpdateBy(getUserName());
        sysDept.setUpdateTime(new Date());
        sysDeptService.updateByPrimaryKeySelective(sysDept);
        return new Result("修改成功");
    }

    @DeleteMapping("/delete/{deptId}")
    public Result delete(@PathVariable Long deptId) {

        if (sysDeptService.selectByParentId(deptId) > 0) {
            return new Result(ResultEnums.ERROR.getCode(),"存在子部门,无法删除");
        }
        sysDeptService.deleteByPrimaryKey(deptId);
        return new Result("删除成功");
    }

    @GetMapping("/get/{deptId}")
    public Result get(@PathVariable Long deptId) {
        return new Result("查询成功", sysDeptService.get(deptId));
    }
}
