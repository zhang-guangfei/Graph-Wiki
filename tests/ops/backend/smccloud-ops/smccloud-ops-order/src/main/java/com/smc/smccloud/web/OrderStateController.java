package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.OrderLog.OrderStateLogDO;
import com.smc.smccloud.model.order.RcvSaleDTO;
import com.smc.smccloud.model.orderstate.OrderStateDTO;
import com.smc.smccloud.model.orderstate.OrderStateLogRequest;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.OrderStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/9/14 16:05
 * @Descripton TODO
 */

@RestController
@Slf4j
@RequestMapping("/order/state")
public class OrderStateController {

    @Resource
    private OrderStateService orderStateService;

    @RequestMapping(value = "/getSplitOrderState", method = RequestMethod.GET)
    public ResultVo<List<OrderStateDTO>> getSalesDateByModelNo(@RequestParam("rorderNo") String rorderNo, @RequestParam("itemNo") String itemNo) {
        return orderStateService.getSplitOrderState(rorderNo, itemNo);
    }

    /**
     * 获取交货期日志记录
     */
    @PostMapping("/getOrderStateLogList")
    public ResultVo<PageInfo<OrderStateLogDO>> getOrderStateLogList(@RequestBody OrderStateLogRequest request,
                                                                    @RequestParam("pageNumber") int pageNumber,
                                                                    @RequestParam("pageSize") int pageSize) {
        return orderStateService.getOrderStateLogList(request, pageNumber,pageSize);
    }


}
