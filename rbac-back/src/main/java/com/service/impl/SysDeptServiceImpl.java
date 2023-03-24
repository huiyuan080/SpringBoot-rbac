package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.mapper.SysDeptMapper;
import com.domain.SysDept;
import com.service.SysDeptService;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/10 
 * @apiNote 
 */
@Service
public class SysDeptServiceImpl implements SysDeptService{

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public int deleteByPrimaryKey(Long deptId) {
        return sysDeptMapper.deleteByPrimaryKey(deptId);
    }

    @Override
    public int insert(SysDept record) {
        return sysDeptMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDept record) {
        return sysDeptMapper.insertSelective(record);
    }

    @Override
    public SysDept selectByPrimaryKey(Long deptId) {
        return sysDeptMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDept record) {
        return sysDeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDept record) {
        return sysDeptMapper.updateByPrimaryKey(record);
    }

    @Override
    public int selectByParentId(Long deptId) {
        return sysDeptMapper.selectByParentId(deptId);
    }

    @Override
    public SysDept get(Long deptId) {
        return sysDeptMapper.get(deptId) ;
    }

    @Override
    public List<SysDept> listDept(SysDept sysDept) {
        return  sysDeptMapper.listDept(sysDept);
    }

}
