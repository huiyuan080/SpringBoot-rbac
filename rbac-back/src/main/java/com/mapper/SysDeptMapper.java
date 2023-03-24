package com.mapper;

import com.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/10
 * @apiNote
 */
public interface SysDeptMapper {
    int deleteByPrimaryKey(Long deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> listDept(@Param("sysDept") SysDept sysDept);

    SysDept get(@Param("deptId") Long deptId);

    int selectByParentId(Long deptId);
}