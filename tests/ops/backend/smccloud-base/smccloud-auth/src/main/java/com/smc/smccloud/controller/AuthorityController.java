package com.smc.smccloud.controller;

import com.smc.smccloud.model.authority.Authority;
import com.smc.smccloud.model.authority.AuthorityCondition;
import com.smc.smccloud.model.authority.AuthorityMenu;
import com.smc.smccloud.model.authority.AuthorityResource;
import com.smc.smccloud.service.AuthService;
import com.smc.smccloud.service.authority.AuthorityMenuService;
import com.smc.smccloud.service.authority.AuthorityResourceService;
import com.smc.smccloud.service.authority.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping(value="/authority")
public class AuthorityController
{
    @Resource
    private AuthorityService authorityService;

    @Resource
    private AuthorityResourceService authorityResourceService;

    @Resource
    private AuthorityMenuService authorityMenuService;

    @Resource
    private AuthService authService;
    /**
     * 获取全部节点
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Authority> findAll(AuthorityCondition condition) {
        return authorityService.findAll(condition);
    }


    /**
     * 根据角色ID获取用户对应的权限
     * @return
     */
    @RequestMapping(value = "/findByRoleId/{roleId}", method = RequestMethod.GET)
    public Set<Authority> findByRoleId(@PathVariable("roleId") String roleId){
        return authorityService.findByRoleId(roleId);
    }

    /**
     * 通过权限ID获取绑定的资源   by authorityId  get resource
     * @return
     */
    @RequestMapping(value = "/findBindResourceById/{authorityId}", method = RequestMethod.GET)
    @ResponseBody
    public List<AuthorityResource> findBindResourceById(@PathVariable(value = "authorityId") String authorityId) {
        return authorityResourceService.findByAuthorityId(authorityId);
    }

    /**
     * 通过权限ID获取绑定的菜单  by authorityId  get menu
     * @return
     */
    @RequestMapping(value = "/findBindMenuById/{authorityId}", method = RequestMethod.GET)
    @ResponseBody
    public List<AuthorityMenu> findBindMenuById(@PathVariable(value = "authorityId") String authorityId) {
        return authorityMenuService.findByAuthorityId(authorityId);
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Authority add(@RequestBody Authority authority) {
        return authorityService.saveAuthority(authority);
    }
    /**
     * 更新
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Authority update(@RequestBody Authority form) {
        return authorityService.updateAuthority(form);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/deleteByCode/{code}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteByCode(@PathVariable(value = "code") String code) {
        authorityService.deleteByCode(code);
    }

    /**
     * 绑定
     * @return
     */
    @RequestMapping(value = "/bind/menu", method = RequestMethod.POST)
    @ResponseBody
    public void bind(@RequestBody AuthorityMenu authorityMenu) {
        authorityMenuService.saveAuthMenu(authorityMenu);
    }


    /**
     * 绑定
     * @return
     */
    @RequestMapping(value = "/bind/resource/{authorityId}", method = RequestMethod.POST)
    @ResponseBody
    public void bind(@PathVariable(value = "authorityId") String authorityId,@RequestParam(value="resourceIds") List<String> resourceIds) {
        authorityResourceService.add(authorityId, resourceIds);
    }
}
