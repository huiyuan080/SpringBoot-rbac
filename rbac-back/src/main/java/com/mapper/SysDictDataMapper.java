package com.mapper;

import com.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/9
 * @apiNote
 */
public interface SysDictDataMapper {
    int deleteByPrimaryKey(Long dictCode);

    int insert(SysDictData record);

    int insertSelective(SysDictData record);

    SysDictData selectByPrimaryKey(Long dictCode);

    int updateByPrimaryKeySelective(SysDictData record);

    int updateByPrimaryKey(SysDictData record);

    List<SysDictData> getByPage(@Param("params") Map<String, Object> params);

    List<SysDictData> getDictDataByDictType(@Param("dictType") String dictType);
}