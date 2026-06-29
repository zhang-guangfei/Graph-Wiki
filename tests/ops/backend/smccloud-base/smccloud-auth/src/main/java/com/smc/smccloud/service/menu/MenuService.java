package com.smc.smccloud.service.menu;

import com.smc.smccloud.model.menu.Menu;

import java.util.List;

public interface MenuService  {

    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> findAll();

    /**
     * 详情
     * @param id
     * @return
     */
    Menu detail(String id);

    /**
     * 删除
     */
    void deleteByCode(String code);

    /**
     * 根据用户ID获取用户对应的菜单
     * @return
     */
    List<Menu> findByUserId(String userId, List<String> groupIds);

    Menu saveMenu(Menu menu);
}
