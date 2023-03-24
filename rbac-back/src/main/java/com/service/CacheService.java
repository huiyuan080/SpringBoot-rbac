package com.service;


import com.domain.SysDictData;

import java.util.List;

/**
 * @author huiyuan080
 * @date 2023/3/9
 * @apiNote
 */

public interface CacheService {
    void loadDictTypeData();
    List<SysDictData> getDictDataByDictType(String dictType);
}
