package com.mapper;

import com.domain.SysReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/17 
 * @apiNote 
 */
public interface SysReplyMapper {
    int deleteByPrimaryKey(Integer replyId);

    int insert(SysReply record);

    int insertSelective(SysReply record);

    SysReply selectByPrimaryKey(Integer replyId);

    int updateByPrimaryKeySelective(SysReply record);

    int updateByPrimaryKey(SysReply record);

    List<SysReply> get(@Param("topicId") Integer topicId);

    int countNum(@Param("topicId") Integer topicId);
}