package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.apache.xpath.operations.Bool;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthService {
 
     ResultVo<Boolean> hasPermission(String url);


     Boolean hasFuncPermission(String authName,String loginUserNo);
    
}
