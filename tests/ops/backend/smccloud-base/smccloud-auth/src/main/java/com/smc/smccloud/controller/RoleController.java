package com.smc.smccloud.controller;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.authority.DataFilter;
import com.smc.smccloud.model.authority.DataRole;
import com.smc.smccloud.model.authority.RoleAuthority;
import com.smc.smccloud.model.login.Role;
import com.smc.smccloud.model.login.RoleCondition;
import com.smc.smccloud.service.resource.DataFilterService;
import com.smc.smccloud.service.resource.DataRoleService;
import com.smc.smccloud.service.userRole.RoleAuthorityService;
import com.smc.smccloud.service.userRole.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private DataFilterService dataFilterService;

    @Resource
    private RoleAuthorityService roleAuthorityService;

    @Resource
    private DataRoleService dataRoleService;
    /**
     * 所有角色
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<Role> findAll() {
        return roleService.findAll();
    }

    /**
     * 查询 分页查询所有角色信息
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageInfo<Role> query(RoleCondition condition, Page page) {
        return roleService.query(condition,page);
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void add(@RequestBody List<Role> roleList) {
        roleService.add(roleList);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@PathVariable(value = "id") String id) {
        roleService.deleteByRoleId(id);
    }

    /**
     * 更新角色
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public int update(@RequestBody Role form) {
        return roleService.update(form);
    }

    /**
     * 通过角色ID获取绑定的权限
     * @return
     */
    @RequestMapping(value = "/findDataFilterByRoleId", method = RequestMethod.GET)
    public DataFilter findByRoleId(String id) {
        return dataFilterService.findByRoleId(id);
    }

    /**
     * 通过角色ID获取绑定的权限 (功能权限)
     * @return
     */
    @RequestMapping(value = "/findBindAuthorityById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<RoleAuthority> findBindAuthorityById(@PathVariable(value = "id") String id) {
        return roleAuthorityService.findByRoleId(id);
    }

    /**
     * 绑定权限
     * @return
     */
    @RequestMapping(value = "/bind/authority/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void bind(@PathVariable(value = "id") String id, @RequestBody List<String> authorityIds) {
        roleAuthorityService.add(id, authorityIds);
    }

    /**
     * 通过角色ID获取
     * @return
     */
    @RequestMapping(value = "/findDataRoleByRoleId", method = RequestMethod.GET)
    public List<DataRole> findDataRoleByRoleId(String id) {
        return dataRoleService.findByRoleId(id);
    }
}
