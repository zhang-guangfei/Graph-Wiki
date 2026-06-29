package com.sales.ops.feign;

import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.CancelInputForOrderDto;
import com.sales.ops.dto.order.OpsOrderModifyDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.zd.ZDPageShowOrderBindInvDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author C12961
 * @date 2022/4/2 16:08
 */
@FeignClient(name = "wm-service", contextId = "order")
public interface OpsOrderFeignApi {


    // 转定
    @PostMapping("/zd/handleZD")
    CommonResult<String> handleZD(@RequestBody ZDPageShowOrderBindInvDto param);


    /**
     * @description 变更客户订单
     * 必填项：
     * 客户单号    orderId
     * 客户项号    orderItem
     * 变更原因    remark1
     * 操作人员    userDto
     * 变更项：
     * 是否为主单号   master
     * 变更出库方式   dlvEntire
     * 变更集约方式   dlvType
     *
     * 客户交货期     dlvDate
     * 物流交货期     expDlvDate
     *
     * 拆分子型号发货 assModelChangeDo
     *
     * 变更是否自提   specialFlag{1：特发；2：普通订单；3：自提}
     * 变更承运商     carried
     * 变更发货地址   province、city、district、postcode、
     * customerNo、customerName、dlvAddress、
     * linkman、mobile
     * @author C12961
     * @date 2022/3/25 8:42
     */
    @PostMapping("/order/rcvorder/modify/handle")
    CommonResult modifyRcvOrder(@RequestBody OpsOrderModifyDto updateDto);

    @GetMapping("/order/rcvorder/modify/dlvdate/limit")
    CommonResult<Date> getModifyDlvDateLimit(@RequestParam String orderNo, @RequestParam Integer orderItem);

    /**
     * @return code:200 自动取消成功
     * code:201 需要人工确认
     * code:500 取消失败，其他原因
     * @description 自动取消客户订单
     * @author C12961
     * @date 2022/3/24 14:51
     */
    @PostMapping("/order/rcvorder/cancel/auto")
    CommonResult<String> autoCancelRcvOrder(@RequestBody CancelForOrderDto cancelForOrderDto);

    /**
     * @description 手动取消客户订单
     * @author C12961
     * @date 2022/3/25 8:42
     */
    @PostMapping("/order/rcvorder/cancel")
    CommonResult cancelRcvOrder(@RequestBody CancelInputForOrderDto calcelDto);

    @GetMapping("/order/tms/route/get")
    CommonResult getOrderRoute(@RequestParam String expressNo);


}
