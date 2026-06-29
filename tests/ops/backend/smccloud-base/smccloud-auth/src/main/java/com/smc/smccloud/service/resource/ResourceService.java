package com.smc.smccloud.service.resource;


import com.smc.smccloud.model.login.Resource;
import com.smc.smccloud.model.login.ResourceDO;

import java.util.List;

public interface ResourceService {
    /**
     * 获取所有资源
     * @return
     */
    List<ResourceDO> findAll();

    ResourceDO saveResource(ResourceDO resource);

    void deleteByCode(String code);

}
