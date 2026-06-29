package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.UUIDGenerator;
import com.smc.smccloud.mapper.Menu.MenuMapper;
import com.smc.smccloud.model.menu.Menu;
import com.smc.smccloud.service.menu.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAll() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1", 1);
        return menuMapper.selectList(queryWrapper);
    }

    @Override
    public Menu detail(String id) {
        return null;
    }


    @Override
    public void deleteByCode(String code) {
        menuMapper.deleteByCode(code);
    }

    @Override
    public List<Menu> findByUserId(String userId, List<String> groupIds) {
        List<Menu> list = menuMapper.getByUserId(userId);
        //list.addAll(menuDao.getByGroupName(groupNames));
        list.addAll(menuMapper.getByGroupId(groupIds));
        List<String> ids = new ArrayList<>();
        List<Menu> listNew = new ArrayList<>();
        for(Menu menu : list) {
            if(!ids.contains(menu.getId())) {
                ids.add(menu.getId());
                listNew.add(menu);
            }
        }
        return listNew;
    }

    @Override
    public Menu saveMenu(Menu menu) {
        if (PublicUtil.isEmpty(menu.getId())) {
            menu.setId(UUIDGenerator.getUUID());
            menu.setCode(UUIDGenerator.getRandomNum());
            menuMapper.insert(menu);
            return menu;
        } else {
            menuMapper.updateById(menu);
            return menu;
        }

    }
}
