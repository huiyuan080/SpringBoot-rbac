package com.service.impl;

import com.common.Page;
import com.github.pagehelper.PageHelper;
import com.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.mapper.SysDictDataMapper;
import com.domain.SysDictData;
import com.service.SysDictDataService;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/9
 * @apiNote
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Resource
    private CacheService cacheService;

    @Override
    public int deleteByPrimaryKey(Long dictCode) {
        return sysDictDataMapper.deleteByPrimaryKey(dictCode);
    }

    @Override
    public int insert(SysDictData record) {
        return sysDictDataMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDictData record) {
        return sysDictDataMapper.insertSelective(record);
    }

    @Override
    public SysDictData selectByPrimaryKey(Long dictCode) {
        return sysDictDataMapper.selectByPrimaryKey(dictCode);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDictData record) {
        return sysDictDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDictData record) {
        return sysDictDataMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page<Object> pageHelper = PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        sysDictDataMapper.getByPage(page.getParams());
        page.setList(pageHelper.getResult());
        page.setTotalCount(pageHelper.getTotal());
        return page;
    }

    @Override
    public List<SysDictData> getDictDataByDictType(String dictType) {
       return cacheService.getDictDataByDictType(dictType);
    }
}
