package com.controller;

import com.common.Page;
import com.common.Result;
import com.domain.SysNotice;
import com.service.SysNoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huiyuan080
 * @date 2023/3/22
 * @apiNote
 */
@RestController
@RequestMapping("sysNotice")
public class SysNoticeController extends BaseController{

    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 分页查询
     * @param page
     * @return
     */
    @PostMapping("getByPage")
    public Result getByPage(@RequestBody Page page) {
        return new Result(sysNoticeService.getByPage(page));
    }

    /**
     * 添加数据
     * @param sysNotice
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysNotice sysNotice) {
        sysNotice.setCreateBy(getUserName());
        sysNotice.setCreateTime(new Date());
        sysNoticeService.insertSelective(sysNotice);
        return new Result("添加成功");
    }

    /**
     * 更新数据
     * @param sysNotice
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysNotice sysNotice) {
        sysNotice.setUpdateBy(getUserName());
        sysNotice.setUpdateTime(new Date());
        sysNoticeService.updateByPrimaryKey(sysNotice);
        return new Result("修改成功");
    }

    /**
     * 根据id查询
     * @param noticeId
     * @return
     */
    @GetMapping("get/{noticeId}")
    public Result get(@PathVariable Long noticeId) {
        return new Result(sysNoticeService.selectByPrimaryKey(noticeId));
    }


    /**
     * 根据id删除数据
     * @param noticeId
     * @return
     */
    @DeleteMapping("delete/{noticeId}")
    public Result delete(@PathVariable Long noticeId) {
        sysNoticeService.deleteByPrimaryKey(noticeId);
        return new Result("删除成功");
    }




}
