package com.smc.smccloud.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.JwtEntity;
import com.smc.smccloud.mapper.SaleEmployeePositionMapper;
import com.smc.smccloud.model.login.EmployeePosition;
import com.smc.smccloud.model.login.Role;
import com.smc.smccloud.model.login.UserInfo;
import com.smc.smccloud.service.userRole.RoleService;
import com.smc.smccloud.service.userRole.UserRoleService;
import com.smc.smccloud.service.userRole.UserService;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value="/user")
@CrossOrigin
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StringEncryptor encryptor;

    @Resource
    private SaleEmployeePositionMapper saleEmployeePositionMapper;

    @RequestMapping(value = "/findRoleById/{id}", method = RequestMethod.GET)
    public Set<String> findRoleById(@PathVariable(value = "id") String id) {
        return userService.findByUserId(id);
    }

    @RequestMapping(value = "/findBindRoleById/{id}", method = RequestMethod.GET)
    public List<Role> findBindRoleById(@PathVariable(value = "id") String id) {
        return roleService.findByUserId(id);
    }


    @RequestMapping(value = "/bind/role/{id}", method = RequestMethod.POST)
    public void bind(@PathVariable(value = "id") String id, @RequestParam(value = "psnname") String psnname,
                     @RequestParam(value="roleIds") List<String> roleIds)
    {
        userRoleService.add(id, psnname, roleIds);
    }

    /**
     * getUserInfo端，手机端都在用
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ResultVo<UserInfo> getUserInfo(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        // C14717 bugid：14930  2024/8/14
        String ssoValue = request.getHeader("sso");
        if(StringUtils.isNotBlank(ssoValue) && !"undefined".equals(ssoValue)){
            username = encryptor.decrypt(ssoValue);
        }
        return ResultVo.success(userService.findUserByUsername(username, true));
    }

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @RequestMapping("/getUserInfo/{username}")
    public UserInfo getUserInfo(@PathVariable(value = "username") String username) {
        return userService.findUserByUsername(username, true);
    }
/*
    @GetMapping("/getUserInfoTest")
    public String getUserInfoTest(){
        return GetUserInfo.getUserInfo();
    }*/

  @GetMapping("/getDeptNo")
  public List<EmployeePosition> listEmployeePosition(@RequestParam("userName") String userName){
      List<EmployeePosition> byEmployeeId = saleEmployeePositionMapper.getByEmployeeId(userName);
      System.out.println("byEmployeeId = " + byEmployeeId.toString());
      return null;
  }

    @RequestMapping("/index")
    public Object index(@AuthenticationPrincipal AuthenticationPrincipal authenticationPrincipal, HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "Bearer ");
        Claims body = Jwts.parser().setSigningKey(Constants.JWT_TOKEN_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        String s = JSONObject.toJSONString(body);
        JwtEntity jwtEntity = JSON.parseObject(s, JwtEntity.class);
        String deptNo = jwtEntity.getDeptNo();
        System.out.println("deptNo = " + deptNo);
        System.out.println("jwtEntity = " + jwtEntity.toString());
        return jwtEntity;
    }


}
