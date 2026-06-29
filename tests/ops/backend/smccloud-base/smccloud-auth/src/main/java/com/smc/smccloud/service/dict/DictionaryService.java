package com.smc.smccloud.service.dict;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.dict.Dictionary;

import java.util.List;

public interface DictionaryService {

    /**
     * 查询所有
     * @return
     */
    public List<Dictionary> queryAll();

    Dictionary detail(String id);

    /**
     * 新增数据
     * @param addData
     */
    public Dictionary addRecord(Dictionary addData);

    List<Dictionary> getChildsByPid(String pid);

    ResultVo<List<Dictionary>> findChildsByPid(String pid);

}
