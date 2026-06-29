package com.sales.ops.feign.inqb;


import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.dto.inqb.InqbApplyVerifyReurn;
import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.sales.ops.dto.inqb.InqbSalesApplyAddParam;
import com.sales.ops.dto.inqb.InqbSalesApplyAddReturn;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * INQ-B 门户交互接口信息
 */
@FeignClient(name = "delivery-server", contextId = "inqbAdapterServer")
public interface InqbAdapterFeignApi {

    /**
     * 门户调用 INQB催促新增
     */
    @RequestMapping(value = "/do/inqb/handle/salesAddApply", method = RequestMethod.POST)
    ResultVo<InqbSalesApplyAddReturn> addInqb(@RequestBody InqbSalesApplyAddParam inqbSalesApplyAddParam);

    /**
     * 返回给门户催促原因分类码
     * @return
     */
    @RequestMapping(value = "/do/inqb/handle/getAllReason", method = RequestMethod.POST)
    ResultVo<List<OpsInqbCodeConfig>> getInqbSendReason();

    /**
     * 提供给门户可用查询接口
     * 根据客户、用户、最终用户，型号、数量、返回可用的ops的inqbNo
     */
    @RequestMapping(value = "/do/inqb/queryUsage/getInqbUsageList", method = RequestMethod.POST)
    ResultVo<List<InqbQueryRequestParam>> findInqbUsageList(@RequestBody List<InqbQueryRequestParam> inqbQueryRequestParam);


    /**
     * 门户催促新增校验接口，判断传输参数的是否可催促，初始校验
     * @param inqbApplyVerify
     * @return
     */
    @RequestMapping(value = "/do/inqb/handle/salesInqbAddValid", method = RequestMethod.POST)
    ResultVo<InqbApplyVerifyReurn> salesInqbAddValid(@RequestBody InqbApplyVerifyReurn inqbApplyVerify);


}
