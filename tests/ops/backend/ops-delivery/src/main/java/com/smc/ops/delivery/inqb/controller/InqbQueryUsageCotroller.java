package com.smc.ops.delivery.inqb.controller;

import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.smc.ops.delivery.inqb.service.InqbApplyCommonService;
import com.smc.ops.delivery.inqb.service.InqbQueryInfoService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dxf
 * INQB查询及占用
 */

@RestController
@CrossOrigin
@RequestMapping("/inqb/queryUsage")
public class InqbQueryUsageCotroller {

    @Resource
    private InqbQueryInfoService inquiryQueryInfoService;

    @Resource
    private InqbApplyCommonService inquiryApplyCommonService;

    /**
     * INQb查询接口
     * 给门户提供的查询接口
     * 参数:型号（整型号），客户、用户、最终用户，数量（小于等于问询数量）
     * 返回可以使用的INQB申请信息，只返回申请号的集合即可
     * 2024-07-29 可用性校验？？？ AS400接口
     */
    @RequestMapping(value = "/getInqbUsageList", method = RequestMethod.POST)
    public ResultVo<List<InqbQueryRequestParam>> getInqbUsageList(@RequestBody List<InqbQueryRequestParam> inqbQueryRequestParams) {
        try {
            return inquiryQueryInfoService.getUsageInqbNoInfo(inqbQueryRequestParams);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }


}
