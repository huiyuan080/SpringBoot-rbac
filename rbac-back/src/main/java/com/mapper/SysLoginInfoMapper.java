package com.mapper;

import com.domain.SysLoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/22 
 * @apiNote 
 */
public interface SysLoginInfoMapper {
    int deleteByPrimaryKey(Long infoId);

    int insert(SysLoginInfo record);

    int insertSelective(SysLoginInfo record);

    SysLoginInfo selectByPrimaryKey(Long infoId);

    int updateByPrimaryKeySelective(SysLoginInfo record);

    int updateByPrimaryKey(SysLoginInfo record);
    /**
     * 查询登陆日志
     *
     * @param params
     * @return
     */
    List<SysLoginInfo> queryAllSysLoginInfo(@Param("params") Map<String, Object> params);

    /**
     * 批量删除
     *
     * @param id
     */
    void deleteByPrimaryKeys(@Param("id") Long[] id);

    /**
     * 清空登陆日志
     */
    void deleteAllLoginInfo();

}