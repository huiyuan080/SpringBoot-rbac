package com.service;

import com.common.Page;
import com.domain.SysNotice;
    /**
 * @author huiyuan080
 * @date 2023/3/22 
 * @apiNote 
 */
public interface SysNoticeService{


    int deleteByPrimaryKey(Long noticeId);

    int insert(SysNotice record);

    int insertSelective(SysNotice record);

    SysNotice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(SysNotice record);

    int updateByPrimaryKey(SysNotice record);

        Page getByPage(Page page);
    }
