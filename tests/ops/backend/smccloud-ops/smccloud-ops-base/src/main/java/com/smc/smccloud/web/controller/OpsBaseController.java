package com.smc.smccloud.web.controller;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin
public class OpsBaseController {

    @GetMapping(value = "/testAuth")
    @PreAuthorize("hasAnyAuthority('开发')") // 拥有开发权限才可访问该方法
    public ResultVo<Object> TestAuth(){
        String s = "aaaaaa";
        return ResultVo.success(s);
    }
}
