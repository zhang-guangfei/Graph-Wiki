package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.Model.SaleEmployeeVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.model.AdVerifyUtils;
import com.smc.smccloud.model.login.UserSecurity;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.SaleEmployeeMapper;
import com.smc.smccloud.mapper.SaleEmployeePositionMapper;
import com.smc.smccloud.model.login.*;
import com.smc.smccloud.service.authority.AuthorityService;
import com.smc.smccloud.service.employeeAndOrgan.PositionService;
import com.smc.smccloud.service.userRole.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private SaleEmployeeMapper saleEmployeeMapper;

    @Resource
    private SaleEmployeePositionMapper saleEmployeePositionMapper;

    @Resource
    private PositionService positionService;

    @Resource
    private AuthorityService authorityService;

    /**
     * 密码认证
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = findUserByUsername(username, false);
        String password = PublicUtil.isEmpty(userInfo.getRemoteUser().getPassword()) ? "NoPassWord" : userInfo.getRemoteUser().getPassword();
        /**
         * 工作流角色（普通）
         */
        userInfo.getRoles().add("ROLE_ACTIVITI_USER");
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userInfo.getRoles().toArray(new String[0]));
        String deptNos = "";
        if (userInfo.getRemoteUser().getEmployeePositions().size() > 0) {
             deptNos = userInfo.getRemoteUser().getEmployeePositions().get(0).getUnitId();
        }
        UserSecurity userSecurity = new UserSecurity(userInfo.getRemoteUser().getUserId(), password, authorities);
        userSecurity.setPsnName(userInfo.getRemoteUser().getPsnName());
        userSecurity.setDeptNo(deptNos);
        //org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
        return userSecurity;
    }

    @Override
    public UserInfo findUserByUsername(String username, boolean isGetAuthorityCode) {
        SaleEmployee employee = saleEmployeeMapper.selectByUserName(username);

        if (employee == null) {
           throw new RuntimeException(username + "用户不存在");
        }

        List<EmployeePosition> employeePositoins = saleEmployeePositionMapper.getByEmployeeId(username);
        RemoteUser remoteUser = new RemoteUser();
        remoteUser.setUserId(employee.getId());
        remoteUser.setUsername(employee.getId());
        remoteUser.setEmployeePositions(employeePositoins);
        remoteUser.setPsnName(employee.getName());
        remoteUser.setStatus("1");

        /**
         *
         */
        List<EmployeePosition> positions = remoteUser.getEmployeePositions();
        Set<String> roles = positionService.filterAfterRole(username, positions);
        List<EmployeePosition> positionsNew = null;

        if (isGetAuthorityCode) {
            positionsNew = positionService.filterAfterPosition(username, positions, true);
        } else {
            positionsNew = positionService.filterAfterPosition(username, positions, false);
        }

        remoteUser.setEmployeePositions(positionsNew);
        List<String> positionIds = new ArrayList<String>();
        for (EmployeePosition position : positionsNew) {
            positionIds.add(position.getPositionId());
        }

        if (PublicUtil.isEmpty(roles)) {
            log.error(username+"未授权用户");
        }

        UserInfo userInfo = new UserInfo(remoteUser, roles);

        if (isGetAuthorityCode) {
            Set<String> setAuthorityCodes = authorityService.findByUserId(username);
            Set<String> setAuthorityCodesGroupNames = authorityService.findByGroupIds(positionIds);
            setAuthorityCodes.addAll(setAuthorityCodesGroupNames);
            userInfo.setAuthorityCodes(setAuthorityCodes);
        }
        // log.info("findUserByUsername: {}", userInfo.toString());
        return userInfo;
    }

    @Override
    public void validateUserUnLock(String username) {

    }

    @Override
    public void resetFailureCount(String username) {

    }

    @Override
    public Integer[] updateFailureCount(String username) {
        return new Integer[0];
    }

    @Override
    public Set<String> findByUserId(String userId) {
        return null;
    }

    @Override
    public ResultVo<SaleEmployeeVO> findUserInfo(String no) {
        if (StringUtils.isBlank(no)) {
            return ResultVo.failure("参数为空.");
        }
        SaleEmployee saleEmployee = saleEmployeeMapper.selectByUserName(no);
        if (saleEmployee == null) {
            return ResultVo.failure("未查出.");
        }
        return ResultVo.success(BeanCopyUtil.copy(saleEmployee,SaleEmployeeVO.class));
    }


}
