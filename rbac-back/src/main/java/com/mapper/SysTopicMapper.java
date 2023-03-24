package com.mapper;

import com.domain.SysTopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/17 
 * @apiNote 
 */
public interface SysTopicMapper {
    int deleteByPrimaryKey(Integer topicId);

    int insert(SysTopic record);

    int insertSelective(SysTopic record);

    SysTopic selectByPrimaryKey(Integer topicId);

    int updateByPrimaryKeySelective(SysTopic record);

    int updateByPrimaryKey(SysTopic record);

    List<SysTopic> getSysTopicByPage(@Param("params") Map params);

    void add(@Param("topicId") Integer topicId);
}