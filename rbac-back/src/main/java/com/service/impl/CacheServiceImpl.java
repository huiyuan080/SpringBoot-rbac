package com.service.impl;

import com.constants.CoreConstant;
import com.domain.SysDictData;
import com.domain.SysDictType;
import com.enums.StateEnums;
import com.mapper.SysDictDataMapper;
import com.mapper.SysDictTypeMapper;
import com.service.CacheService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huiyuan080
 * @date 2023/3/9
 * @apiNote
 */
@Service
public class CacheServiceImpl implements CacheService {

    private static Map<String, List<SysDictData>> caches = new HashMap<>(8);
    @Resource
    private SysDictTypeMapper sysDictTypeMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public void loadDictTypeData() {
        /*
         *   引入缓存
         *     字典数据 变化频率较低  每次获取字典数据 没有必要到数据库来查询  可以使用缓存来缓存字典数据
         *        如果客户端需要某个字典类型数据 后端提供一个接口 接收字典类型值 根据字典类型值到缓存查找即可
         *   刷新缓存   实际上就是将数据库字典数据查出来放入缓存
         *        将每个字典类型的字典记录存入一个特殊的数据结构中  后续使用时从这个数据结构取即可
         *           sys_user_sex---->List<SysDictData>
         *           sys_notice_type--->List<SysDictData>
         *           sys_login_status--->List<SysDictData>
         *          这个数据结构选着 Map<String,List<SysDictData>>
         */
        List<SysDictType> allDictType = sysDictTypeMapper.getAllDictType(StateEnums.ENABLED.getCode());
        for (SysDictType sysDictType : allDictType) {
            caches.put(sysDictType.getDictType(), sysDictDataMapper.getDictDataByDictType(sysDictType.getDictType()));
        }
    }

    @Override
    public List<SysDictData> getDictDataByDictType(String dictType) {
        /*
         * 根据字典类型获取字典数据
         *   如果caches 存在 直接获取即可
         *   如果caches 不存在(添加 修改 删除 在没有执行缓存同步时 可能caches不存在对应记录)
         *  查找顺序
         *     先从缓存查
         *        缓存存在 直接返回
         *        缓存不存在
         *     到数据库查找
         *         找到对应记录后 返回记录  同时将查到的记录放入caches
         */
        List<SysDictData> sysDictData = caches.get(dictType);
        if (!CollectionUtils.isEmpty(sysDictData)) {
            //如果缓存中存在数据  返回
            return sysDictData;
        }
        //如果不存在  就从数据中找到数据
        sysDictData = sysDictDataMapper.getDictDataByDictType(dictType);
        //这个方法用于将数据库中查到的数据放入缓存中
        setCaches(dictType,sysDictData);
        return caches.get(dictType);
    }

    private void setCaches(String dictType,List<SysDictData> sysDictData) {
        if (!CollectionUtils.isEmpty(sysDictData)) {
            //将从数据库中查询到的数据放入到缓存中
            caches.put(dictType, sysDictData);
        }
    }


}
