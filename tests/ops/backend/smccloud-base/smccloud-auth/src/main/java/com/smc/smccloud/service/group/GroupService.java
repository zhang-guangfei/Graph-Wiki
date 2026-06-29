package com.smc.smccloud.service.group;

import com.smc.smccloud.model.group.Group;

import java.util.List;
import java.util.Set;

public interface GroupService {
    /**
     * 获取所有权限
     * @return
     */
    public List<Group> findAll();

    /**
     * 详情
     * @param id
     * @return
     */
    public Group detail(String id);

    /**
     * 删除
     */
    public void deleteByCode(String code);

    /**
     * 通过用户Id获取按钮权限
     */
    public Set<String> findByUserId(String userId);
}
