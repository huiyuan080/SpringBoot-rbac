package com.service.impl;

import com.common.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.mapper.SysTopicMapper;
import com.domain.SysTopic;
import com.service.SysTopicService;

/**
 * @author huiyuan080
 * @date 2023/3/17
 * @apiNote
 */
@Service
public class SysTopicServiceImpl implements SysTopicService {

    @Resource
    private SysTopicMapper sysTopicMapper;

    @Override
    public int deleteByPrimaryKey(Integer topicId) {
        return sysTopicMapper.deleteByPrimaryKey(topicId);
    }

    @Override
    public int insert(SysTopic record) {
        return sysTopicMapper.insert(record);
    }

    @Override
    public int insertSelective(SysTopic record) {
        return sysTopicMapper.insertSelective(record);
    }

    @Override
    public SysTopic selectByPrimaryKey(Integer topicId) {
        return sysTopicMapper.selectByPrimaryKey(topicId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysTopic record) {
        return sysTopicMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysTopic record) {
        return sysTopicMapper.updateByPrimaryKey(record);
    }


    @Override
    public Page getSysTopicByPage(Page page) {
        com.github.pagehelper.Page<Object> pageHelper = PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        sysTopicMapper.getSysTopicByPage(page.getParams());
        //设置当前页的列表数据
        page.setList(pageHelper.getResult());
        //设置总数&页数
        page.setTotalCount(pageHelper.getTotal());
        return page;
    }

    @Override
    public void add(Integer topicId) {
        sysTopicMapper.add(topicId);
    }
}
