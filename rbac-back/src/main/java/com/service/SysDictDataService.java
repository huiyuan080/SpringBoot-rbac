package com.service;

import com.common.Page;
import com.domain.SysDictData;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/9
 * @apiNote
 */
public interface SysDictDataService {


    int deleteByPrimaryKey(Long dictCode);

    int insert(SysDictData record);

    int insertSelective(SysDictData record);

    SysDictData selectByPrimaryKey(Long dictCode);

    int updateByPrimaryKeySelective(SysDictData record);

    int updateByPrimaryKey(SysDictData record);


    Page getByPage(Page page);

    List<SysDictData> getDictDataByDictType(String dictType);
}
