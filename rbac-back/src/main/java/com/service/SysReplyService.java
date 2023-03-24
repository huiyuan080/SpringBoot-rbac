package com.service;

import com.domain.SysReply;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/17 
 * @apiNote 
 */
public interface SysReplyService{


    int deleteByPrimaryKey(Integer replyId);

    int insert(SysReply record);

    int insertSelective(SysReply record);

    SysReply selectByPrimaryKey(Integer replyId);

    int updateByPrimaryKeySelective(SysReply record);

    int updateByPrimaryKey(SysReply record);


        List<SysReply> get(Integer topicId);

    int countNum(Integer topicId);
}
