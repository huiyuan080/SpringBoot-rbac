package com.controller;

import com.common.Result;
import com.domain.SysReply;
import com.domain.SysTopic;
import com.service.SysReplyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huiyuan080
 * @date 2023/3/17
 * @apiNote
 */
@RestController
@RequestMapping("sysReply")
public class SysReplyController {

    @Resource
    private SysReplyService sysReplyService;

    @GetMapping("get/{topicId}")
    public Result queryDetails(@PathVariable Integer topicId) {
        return new Result(sysReplyService.get(topicId));
    }
    @PostMapping("save")
    public Result save(@RequestBody SysReply sysReply) {
        sysReply.setCreateDate(new Date());
        sysReplyService.insertSelective(sysReply);
        return new Result("添加成功");
    }

    @GetMapping("count/{topicId}")
    public Result countNum(@PathVariable Integer topicId) {
        return new Result(sysReplyService.countNum(topicId));
    }
}
