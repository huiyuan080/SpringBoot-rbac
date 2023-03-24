package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.mapper.SysReplyMapper;
import com.domain.SysReply;
import com.service.SysReplyService;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/17 
 * @apiNote 
 */
@Service
public class SysReplyServiceImpl implements SysReplyService{

    @Resource
    private SysReplyMapper sysReplyMapper;

    @Override
    public int deleteByPrimaryKey(Integer replyId) {
        return sysReplyMapper.deleteByPrimaryKey(replyId);
    }

    @Override
    public int insert(SysReply record) {
        return sysReplyMapper.insert(record);
    }

    @Override
    public int insertSelective(SysReply record) {
        return sysReplyMapper.insertSelective(record);
    }

    @Override
    public SysReply selectByPrimaryKey(Integer replyId) {
        return sysReplyMapper.selectByPrimaryKey(replyId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysReply record) {
        return sysReplyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysReply record) {
        return sysReplyMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysReply> get(Integer topicId) {
        return sysReplyMapper.get(topicId);
    }

    @Override
    public int countNum(Integer topicId) {
        return sysReplyMapper.countNum(topicId);
    }
}
