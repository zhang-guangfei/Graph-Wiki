package com.sales.ops.feign.inventory;

import com.sales.ops.dto.ba.ChangeYYDto;
import com.sales.ops.dto.inventory.WmPCOConfirmDto;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：异常表处理
 * @date ：Created in 2022/11/21 12:24
 */
@FeignClient(name = "ba-service", contextId = "exce")
public interface OpsExcHandleFeignApi {

    /**
     * do异常处理
     * @param
     * @return
     */
    @RequestMapping(value = "/excHandle/batchHandle", method = RequestMethod.GET)
    CommonResult<String> sendDoToWMSChangeStatusTwo(@RequestParam("limit") Integer limit);


    /**
     * bugid:10804
     * 下预约 返回是否可以下预约
     * @param list
     * @return
     */
    @RequestMapping(value = "/excHandle/changeYY", method = RequestMethod.POST)
    CommonResult<List<ChangeYYDto>> changeYY(@RequestBody List<ChangeYYDto> list);

}
