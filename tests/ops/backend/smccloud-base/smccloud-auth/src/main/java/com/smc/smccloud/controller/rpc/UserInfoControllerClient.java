package com.smc.smccloud.controller.rpc;

import com.smc.smccloud.Model.SaleEmployeeVO;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.UserInfoServiceApi;
import com.smc.smccloud.service.userRole.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2022/4/8 11:02
 * @Descripton TODO
 */

@RestController
public class UserInfoControllerClient implements UserInfoServiceApi {

    @Resource
    private UserService userService;

    @Override
    public ResultVo<SaleEmployeeVO> findUserInfo(String no) {
        return userService.findUserInfo(no);
    }
}
