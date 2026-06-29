package com.sales.ops.feign;


import com.sales.ops.db.entity.OpsPrepareOrder;
import com.sales.ops.dto.prepareOrder.CanUsePrepareOrderParam;
import com.sales.ops.dto.prepareOrder.OpsPrepareOrderVO;
import com.sales.ops.dto.prepareOrder.PrepareOrderPreQtyUpdateDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 准备订单模块对外提供feign接口
 */
@FeignClient(name = "po-service", contextId = "prepareOrder")
public interface PrepareOrderFeignApi {

    @PostMapping("/prepareOrder/prepareOrderVerify")
    ResultVo<Map<String, Boolean>> prepareOrderVerify(@RequestBody List<String> modelNos);

    @PostMapping("/prepareOrder/prepareOrderCreate")
    ResultVo<String> prepareOrderCreate(@RequestBody List<OpsPrepareOrder> opsPrepareOrders);

    /**
     * 向PSI推送准备订单
     */
    @GetMapping("/prepareOrder/pushPrepareOrderToPSI")
    ResultVo<String> pushPrepareOrderToPSI();

    /**
     * 从PSI拉取返信信息
     */
    @GetMapping("/prepareOrder/pullPrepareOrderDeliveryInfoFromPsi")
    ResultVo<String> pullPrepareOrderDeliveryInfoFromPsi();

    /**
     * 获取可使用的准备订单清单
     */
    @PostMapping("/prepareOrder/getAvailablePrepareOrderList")
    ResultVo<OpsPrepareOrderVO> getAvailablePrepareOrderList(@RequestBody CanUsePrepareOrderParam param);

    /**
     * 准备订单预处理
     * @return
     */
    @GetMapping("/prepareOrder/prepareOrderPreHandle")
    ResultVo<String> prepareOrderPreHandle();

    /**
     * 追加准备订单预占
     */
    @PostMapping("/prepareOrder/updatePreQty")
    ResultVo<String> updatePreQty(@RequestBody PrepareOrderPreQtyUpdateDto dto);

}
