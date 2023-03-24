package com.controller;

import com.common.Page;
import com.common.Result;
import com.domain.SysTopic;
import com.service.SysTopicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huiyuan080
 * @date 2023/3/17
 * @apiNote
 */
@RestController
@RequestMapping("sysTopic")
public class SysTopicController extends BaseController {
    @Resource
    private SysTopicService sysTopicService;

    /**
     * 分页查询信息
     *
     * @param page
     * @return
     */
    @PostMapping("getByPage")
    public Result getByPage(@RequestBody Page page) {
        return new Result(sysTopicService.getSysTopicByPage(page));
    }

    /**
     * 发布一个贴子
     *
     * @param sysTopic
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysTopic sysTopic) {
        sysTopic.setCreateDate(new Date());
        sysTopic.setClickAmount(0);
        sysTopicService.insertSelective(sysTopic);
        return new Result("添加成功");
    }

    @DeleteMapping("delete/{topicId}")
    public Result delete(@PathVariable Integer topicId) {
        sysTopicService.deleteByPrimaryKey(topicId);
        return new Result("删除成功");
    }

    @GetMapping("add/{topicId}")
    public Result add(@PathVariable Integer topicId){
        sysTopicService.add(topicId);
        return new Result();
    }

}
