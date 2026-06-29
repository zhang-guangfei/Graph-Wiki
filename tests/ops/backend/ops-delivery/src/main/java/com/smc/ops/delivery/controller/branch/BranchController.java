package com.smc.ops.delivery.controller.branch;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.CommonResult;
import com.smc.ops.delivery.service.branch.BranchService;
import com.smc.ops.delivery.service.branch.BranchZHService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 13:26
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/branch")
@Slf4j
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchZHService branchZHService;

    /**
     * 16334
     * @return
     * @throws OpsException
     */
    @RequestMapping("/handle")
    public CommonResult<String> handleBranch() throws OpsException{
        try {
            branchService.handleBranch();
        } catch (Exception e) {
            log.error("广州统合",e);
        }
        return CommonResult.success();
    }


    //16354
    @RequestMapping("/zh/handle")
    public CommonResult<String> handleZHBranch() throws OpsException{
        try {
            branchZHService.handleBranch();
        } catch (Exception e) {
            log.error("广州统合组换",e);
        }
        return CommonResult.success();
    }

}
