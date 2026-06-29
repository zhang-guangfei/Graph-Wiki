package com.smc.smccloud.service.impl;

import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.login.EmployeePosition;
import com.smc.smccloud.model.login.Role;
import com.smc.smccloud.service.employeeAndOrgan.PositionService;
import com.smc.smccloud.service.userRole.RoleService;
import com.smc.smccloud.util.FilterPostion;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class PositionServiceImpl implements PositionService
{
    @Resource
    private FilterPostion filterPostion;

    @Resource
    private RoleService roleService;

    @Override
    public List<EmployeePosition> filterAfterPosition(String username, List<EmployeePosition> positions, boolean boolHl){
        List<EmployeePosition> positionsNew = new LinkedList<EmployeePosition>();
        for(EmployeePosition position : positions) {
            String unitId = position.getUnitId();
            //List<Role> listGroupRole = roleService.findByGroupId(position.getPositionId());
            boolean bool = true;
            for(String departPrefix : filterPostion.getDepartPrefixs() ) {
                if(StringUtils.equals(StringUtils.substring(unitId, 0, 1), departPrefix)
                    //&& CollectionUtils.isNotEmpty(listGroupRole)
                ) {
                    if(bool) {
                        positionsNew.add(position);
                        bool = false;
                    }
                }
            }
        }
        if(PublicUtil.isEmpty(positionsNew)) {
            positionsNew = positions;
        }
        return positionsNew;
    }

    @Override
    public Set<String> filterAfterRole(String username, List<EmployeePosition> positions){
        List<Role> listRole = roleService.findByUserId(username);

        for(EmployeePosition position : positions) {
            List<Role> listGroupRole = roleService.findByGroupId(position.getPositionId());
            if(PublicUtil.isNotEmpty(listGroupRole)) {
                listRole.addAll(listGroupRole);
            }
        }
        Set<String> roles = new HashSet<String>();
        if (listRole == null || listRole.isEmpty()) {
            return roles;
        }
        for(Role role : listRole) {
            roles.add(role.getName());
        }
        return roles;
    }
}
