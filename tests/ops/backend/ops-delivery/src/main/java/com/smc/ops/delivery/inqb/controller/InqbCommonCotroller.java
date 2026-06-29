package com.smc.ops.delivery.inqb.controller;

import com.smc.ops.delivery.inqb.service.InqbApplyCommonService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dxf
 */

@RestController
@CrossOrigin
@RequestMapping("/inqb/queryCommon")
public class InqbCommonCotroller {

    @Resource
    private InqbApplyCommonService inquiryApplyCommonService;



    /**
     * 根据最终用户以及营业所代码，来确定订单的所属区域，南方订单OR北方订单
     * bug18271 OPS给PMS发单计算南北方标识的修改
     */
    @RequestMapping(value ="/findOrderArea",method = RequestMethod.GET)
    public  ResultVo<String> findOrderAreaInfo(@RequestParam(name = "endUser", required = true) String endUser,
                                                                        @RequestParam(name = "deptNo", required = true) String deptNo) throws Exception {
        try {
            return inquiryApplyCommonService.getOrderAreaInfo(endUser, deptNo);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }


}
