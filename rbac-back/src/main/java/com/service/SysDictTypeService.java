package com.service;

import com.common.Page;
import com.domain.SysDictType;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/8 
 * @apiNote 
 */
public interface SysDictTypeService{


    int deleteByPrimaryKey(Long dictId);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);

        Page getSysDictTypeByPage(Page page);

        List<SysDictType> getAllDictType();

    void refreshCache();

}
