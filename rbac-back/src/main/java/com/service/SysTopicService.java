package com.service;

import com.common.Page;
import com.domain.SysTopic;
    /**
 * @author huiyuan080
 * @date 2023/3/17 
 * @apiNote 
 */
public interface SysTopicService{


    int deleteByPrimaryKey(Integer topicId);

    int insert(SysTopic record);

    int insertSelective(SysTopic record);

    SysTopic selectByPrimaryKey(Integer topicId);

    int updateByPrimaryKeySelective(SysTopic record);

    int updateByPrimaryKey(SysTopic record);

        Page getSysTopicByPage(Page page);

        void add(Integer topicId);
    }
