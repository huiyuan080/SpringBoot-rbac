package com.service;

import com.domain.SysDept;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/10 
 * @apiNote 
 */
public interface SysDeptService{


    int deleteByPrimaryKey(Long deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);


    List<SysDept> listDept(SysDept sysDept);

    SysDept get(Long deptId);

    int selectByParentId(Long deptId);
}
