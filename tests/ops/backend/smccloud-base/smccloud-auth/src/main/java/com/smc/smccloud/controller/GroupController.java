package com.smc.smccloud.controller;

import com.smc.smccloud.model.group.Group;
import com.smc.smccloud.model.group.GroupRole;
import com.smc.smccloud.service.group.GroupRoleService;
import com.smc.smccloud.service.group.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value="/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @Resource
    private GroupRoleService groupRoleService;
    /**
     * 获取全部节点
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> findAll() {
        return groupService.findAll();
    }

    /**
     * 通过用户组ID获取绑定的角色
     * @return
     */
    @RequestMapping(value = "/findBindRoleById/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupRole> findBindRoleById(@PathVariable(value = "groupId") String groupId) {
        return groupRoleService.findByGroupId(groupId);
    }

    /**
     * 绑定
     * @return
     */
    @RequestMapping(value = "/bind/role", method = RequestMethod.POST)
    @ResponseBody
    public void bind(@RequestBody Map<String,List<String>> map) {
        groupRoleService.add(map.get("groupIds"), map.get("groupNames"), map.get("roleIds"));
    }
}
