package com.service.impl;

import com.common.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.mapper.SysNoticeMapper;
import com.domain.SysNotice;
import com.service.SysNoticeService;
/**
 * @author huiyuan080
 * @date 2023/3/22 
 * @apiNote 
 */
@Service
public class SysNoticeServiceImpl implements SysNoticeService{

    @Resource
    private SysNoticeMapper sysNoticeMapper;

    @Override
    public int deleteByPrimaryKey(Long noticeId) {
        return sysNoticeMapper.deleteByPrimaryKey(noticeId);
    }

    @Override
    public int insert(SysNotice record) {
        return sysNoticeMapper.insert(record);
    }

    @Override
    public int insertSelective(SysNotice record) {
        return sysNoticeMapper.insertSelective(record);
    }

    @Override
    public SysNotice selectByPrimaryKey(Long noticeId) {
        return sysNoticeMapper.selectByPrimaryKey(noticeId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysNotice record) {
        return sysNoticeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page<Object> pageHelper = PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        sysNoticeMapper.getByPage(page.getParams());
        page.setList(pageHelper.getResult());
        page.setTotalCount(pageHelper.getTotal());
        return page;
    }

    @Override
    public int updateByPrimaryKey(SysNotice record) {
        return sysNoticeMapper.updateByPrimaryKey(record);
    }

}
