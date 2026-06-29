package com.smc.smccloud.controller;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.OauthClient.OauthClientDetails;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.service.Condition;
import com.smc.smccloud.service.oauthClient.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value="/oauthClientDetails")
public class OauthClientDetailsController
{
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @GetMapping("/query")
    public PageInfo<OauthClientDetails> query(Condition condition, Page page) {
        return oauthClientDetailsService.page(condition, page);
    }

    /**
     * 获取终端ID，密钥
     * @return
     */
    @RequestMapping(value = "/findClientId", method = RequestMethod.GET)
    public ResultVo<Map<String, String>> findClientId() {
        return ResultVo.success(oauthClientDetailsService.findClient());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVo<String> add(OauthClientDetails oauthClientDetails) {
        oauthClientDetailsService.saveOauthClient(oauthClientDetails);
        return ResultVo.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVo<String> update(OauthClientDetails oauthClientDetails) {
        oauthClientDetailsService.updateOauthClient(oauthClientDetails);
        return ResultVo.success();
    }

    @RequestMapping(value = "/delete/{clientId}", method = RequestMethod.POST)
    public ResultVo<String> delete(@PathVariable(value = "clientId") String clientId) {
        oauthClientDetailsService.delete(clientId);
        return ResultVo.success();
    }
}
