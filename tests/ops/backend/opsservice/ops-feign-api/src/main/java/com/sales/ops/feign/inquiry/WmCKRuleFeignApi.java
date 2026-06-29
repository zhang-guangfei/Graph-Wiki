package com.sales.ops.feign.inquiry;

import com.sales.ops.dto.delivery.ExpPoDto;
import com.sales.ops.dto.delivery.InqAExpPoRequestDto;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotRequest;
import com.sales.ops.dto.inqb.OpsInqbOrderAllotResult;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/6/13 15:08
 */
@FeignClient(name = "wm-service", contextId = "inquiryBCallWm")
public interface WmCKRuleFeignApi {

    /**
     * bugid:14454 inq-b 订单分配接口
     * @param param
     * @return
     */
    @RequestMapping(value = "/wm/inq/b", method = RequestMethod.POST)
    ResultVo<List<OpsInqbOrderAllotResult>> inqBGetCKRule(@RequestBody OpsInqbOrderAllotRequest param);

    /**
     *  bugid:14912 20240827 c14717
     *  inq-a 根据输入的客户期望货期倒算相应的采购单的期望发货日
     * @param param
     * @return
     */
    @RequestMapping(value = "/wm/inq/a/exp", method = RequestMethod.POST)
    ResultVo<List<ExpPoDto>> inqAGetPoExpDate(@RequestBody InqAExpPoRequestDto param);
}
