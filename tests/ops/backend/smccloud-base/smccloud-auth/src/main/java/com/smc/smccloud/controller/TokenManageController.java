package com.smc.smccloud.controller;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.tokenManage.TokenManageCondition;
import com.smc.smccloud.service.tokenManage.TokenManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/tokenManage")
public class TokenManageController {

    @Autowired
    private TokenManageService tokenManageService;

    @GetMapping("/token")
    public PageInfo<OAuth2AccessToken> query(TokenManageCondition condition, Page page) {
        return tokenManageService.query(condition, page);
    }

    @RequestMapping(value = "/delete/{token}", method = RequestMethod.POST)
    public ResultVo<String> delete(@PathVariable(value = "token") String token) {
        tokenManageService.delete(token);
        return ResultVo.success();
    }

}
