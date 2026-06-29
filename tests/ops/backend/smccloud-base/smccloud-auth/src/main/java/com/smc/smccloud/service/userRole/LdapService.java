package com.smc.smccloud.service.userRole;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;


import java.util.Map;

public interface LdapService
{
    boolean login(String username, String password) throws AuthenticationException, NamingException;

    ResultVo<String> validate(String username, String password);


    Boolean verify(Map<String, String> map);
}
