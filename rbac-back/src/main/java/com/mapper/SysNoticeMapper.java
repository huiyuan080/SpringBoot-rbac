package com.mapper;

import com.domain.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/22 
 * @apiNote 
 */
public interface SysNoticeMapper {
    int deleteByPrimaryKey(Long noticeId);

    int insert(SysNotice record);

    int insertSelective(SysNotice record);

    SysNotice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(SysNotice record);

    int updateByPrimaryKey(SysNotice record);

    List<SysNotice> getByPage(@Param("params") Map<String, Object> params);
}