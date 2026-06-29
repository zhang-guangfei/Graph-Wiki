package com.sales.ops.feign;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.expdetail.TransferVO;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.*;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.dto.purchase.PurchaseUpdateInfo;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/25 11:30
 * @description：提供给单据的API
 */
@FeignClient(name = "wm-service", contextId = "WmDispatchOrder")
public interface OpsWmDispatchForOrderFeignApi {

    @PostMapping(value = "/transferConfrim/handle")
    CommonResult<String> transferConfrim(@RequestBody TransferVO param);


	// bugid:11487 C14717 20230811 反算物流发货日接口修改
	@GetMapping(value = "/order/getOrderMaxWlday")
	CommonResult<String> getOrderMaxWlday(@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "orderItem") String orderItem,
			@RequestParam(value = "dlvDateParam") Date dlvDateParam);

	@GetMapping(value = "/order/cust/master")
	CommonResult<Rcvmaster> getcustOrder(@RequestParam(value = "rorderNo") String rorderNo);

	// @GetMapping("/cust/dispatch/result")
	// CommonResult<List<OpsOrderDispatchView>>
	// getOpsOrderDispatchViews(@RequestParam(value = "rorderNo") String
	// rorderNo,
	// @RequestParam(value = "rorderItem") int rorderItem);

	@GetMapping(value = "/order/cust/orderitems/evenNumber")
	CommonResult<List<Rcvdetail>> getcustdetailByEvenNumber(@RequestParam(value = "limit") int limit);

	@GetMapping(value = "/order/cust/orderitems/oddNumber")
	CommonResult<List<Rcvdetail>> getcustdetailByOddNumber(@RequestParam(value = "limit") int limit);

	@GetMapping(value = "/order/cust/orderitems")
	CommonResult<List<Rcvdetail>> getcustdetail(@RequestParam(value = "limit") int limit);

	@GetMapping(value = "/order/cust/orderitemsByBeforeMonth")
	CommonResult<List<Rcvdetail>> getRcvdetailListByBeforeMoth(@RequestParam(value = "limit") int limit,
			@RequestParam(value = "beforeMonth") int beforeMonth);

	@GetMapping(value = "/order/cust/getRcvdetail")
	CommonResult<Rcvdetail> getRcvdetail(@RequestParam(value = "rorderNo") String rorderNo,
			@RequestParam(value = "rorderItem") Integer rorderItem);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为客户订单提供调度服务
	 */
	@PostMapping(value = "/order/cust/dispatch")
	CommonResult<String> dispatchForcustOrder(@RequestBody DispatchForOrderItemInputDto inputDto);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为所有订单提供取消服务
	 */
	/*
	 * @PostMapping(value = "/order/cancel") CommonResult<String>
	 * cancelForOrder(@RequestBody CancelForOrderDto inputDto);
	 */

	/**
	 * @author ：
	 * @date ： @description： 主动调拨取消 需要传用户 原因
	 */
	@PostMapping(value = "/order/tkck/cancel")
	CommonResult<String> cancelTKCKForOrder(@RequestBody CancelForOrderDto inputDto);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为调账单提供的调度服务
	 */
	@PostMapping(value = "/order/Adjust")
	CommonResult<String> Adjust(@RequestBody InventoryForAdjustInputDto inputDto);

	@PostMapping(value = "/inventory/adjust")
	CommonResult<AdjustParam> adjustInventory(@RequestBody AdjustParam param);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为调账单提供的调度服务
	 */
	@PostMapping(value = "/order/wms/adjust")
	CommonResult<String> wmsAdjust(@RequestBody InventoryForAdjustInputDto inputDto);

	/**
	 * @description：物流管理模块为情报占用库存提供的调度服务 必填项：
	 * @params orderId 订单号
	 * @params modelno 型号
	 * @params deptno 部门ID
	 * @params qty 情报占用数量
	 * @params warehouseCode 仓库代码
	 * @params customerNo 客户代码
	 * @params salesInfoNo 情报号
	 * @params expDate 情报占用到期时间
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 */
	@PostMapping(value = "/order/salesInfo")
	CommonResult<String> salesInfo(@RequestBody InventoryForAdjustDto inputDto);

	/**
	 * @description：物流管理模块为情报占用库存提供的job服务 必填项：
	 * @params orderId 出入库指令号
	 * @params salesInfoNo 情报号
	 */
	@GetMapping("/order/salesInfo/undo")
	CommonResult<String> undoSalesInfo(@RequestParam("salesInfoNo") String salesInfoNo);

	@GetMapping(value = "/warehouseManage/inventory/salesInfos/exp")
	CommonResult findExpireSalesInfo();

	@GetMapping(value = "/warehouseManage/inventory/salesInfos/exp/deleted")
	CommonResult findExpireSalesInfoDeleted(@RequestParam Integer days);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为调库单提供的调度服务
	 */
	@PostMapping(value = "/order/trans/dispatch")
	CommonResult<String> Trans(@RequestBody InventoryForTransInputDto inputDto);

	@PostMapping("/order/trans/create")
	CommonResult<String> createTransOrder(@RequestBody TransOrderDto dto);

	@PostMapping("/order/trans/create/transaction")
	CommonResult<String> createTransOrderBatch(@RequestBody TransOrderDto dto);

	// 调库申请查询Move库存
	@PostMapping("/order/trans/query/move")
	CommonResult<List<TransOrderQueryMoveResult>> findMoveForCreateTransOrder(
			@RequestBody TransOrderQueryMoveParam dto);

	@PostMapping("/order/trans/create/move")
	CommonResult<String> createTransOrderForMove(@RequestBody TransOrderDtoForMove dto);

	@PostMapping("/order/trans/create/preorder")
	CommonResult<Map> createTransOrderForPreorderAccount(@RequestBody PreorderAccountTransOrderDto dto);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为组换单提供的调度服务
	 */
	@PostMapping(value = "/order/producChange/dispatch")
	CommonResult<String> ProducChange(@RequestBody InventoryForProducChangeDto inputDto);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为组换单提供的调度服务 wms回传状态
	 */
	@PostMapping(value = "/order/producChange/wmsStatus")
	CommonResult<String> wmsProducChangeStatus(@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "msg") String msg);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为主动加工单提供的调度服务
	 */
	@PostMapping(value = "/order/Pco/dispatch")
	CommonResult<String> InventoryForPco(@RequestBody InventoryForPcoDto inputDto);

	/**
	 * @author ：c02483
	 * @date ：Created in 2021/10/25 13:45
	 * @description：物流管理模块为客户单提供的强制完成服务
	 */
	@PostMapping(value = "/order/finish")
	CommonResult<String> orderFinish(@RequestParam(value = "OrderId") String OrderId,
			@RequestParam(value = "OrderItem") String OrderItem);

	@PostMapping(value = "/order/create/order")
	CommonResult<String> createByorder(@RequestParam(value = "rorderNo") String rorderNo,
			@RequestParam("rorderItem") String rorderItem);

	/**
	 * @description PO模块向WM模块发送：采购转订的订单号和子项号 WM模块取消掉生产中库存和还原订单状态
	 * @author C12961
	 * @date 2022/3/10 13:11
	 */
	@PostMapping("/order/po/reset")
	CommonResult<String> resetForPo(@RequestBody PoToWmDto poDto);

	/**
	 * @description PO模块向WM模块发送：请购预处理的请购单信息
	 * @author C12961
	 * @date 2023/8/31 9:11
	 */
	@PostMapping("/order/po/preprocess")
	CommonResult<String> preprocessForRequestPo(@RequestBody List<OpsRequestpurchase> list);

	/**
	 * @description PO模块向WM模块发送：被请购拦截的请购单信息
	 * @author C12961
	 * @date 2022/2/25 13:11
	 */
	@PostMapping("/order/po/intercept")
	CommonResult<String> interceptForRequestPo(@RequestBody List<OpsRequestpurchase> list);

	/**
	 * @description PO模块向WM模块发送：请购拦截放行的请购单信息
	 * @author C12961
	 * @date 2022/2/25 13:11
	 */
	@PostMapping("/order/po/release")
	CommonResult<String> releaseForRequestPo(@RequestBody List<OpsRequestpurchase> list);

	/**
	 * @description PO模块向WM模块发送：采购单已发送信息
	 * @author C12961
	 * @date 2022/2/25 14:00
	 */
	@PostMapping("/order/po/send")
	CommonResult<String> sendForPo(@RequestBody List<OpsPurchaseStatusToWMDto> list);

	/**
	 * @description PO模块向WM模块发送：供应商已接单信息，WM生成生产中库存
	 * @author ：c02483
	 * @date ：Created in 2021/12/9 20:21
	 */
	@PostMapping(value = "/order/po/accept")
	CommonResult<String> acceptForPo(@RequestBody List<PoToWmDto> list);

	/**
	 * @description 更新采购单信息
	 * @author C12961
	 * @date 2022/6/27 12:43
	 */
	@PostMapping("/order/po/update")
	CommonResult<String> updateForPo(@RequestBody List<PurchaseUpdateInfo> poInfoList);

	/**
	 * @description：第一次发票导入
	 * @author ：c02483
	 * @date ：Created in 2021/12/10 15:25
	 */
	@PostMapping("/order/import/init/Invoice")
	CommonResult<String> InvoniceForPoInit(@RequestBody List<OpsPurchaseinvoice> list);

	/**
	 * @description：关务导入的发票信息
	 * @author ：c02483
	 * @date ：Created in 2021/12/10 15:25
	 */

	@PostMapping("/order/import/confirm/Invoice")
	CommonResult<String> InvoniceForPoConfirm(@RequestBody List<OpsImpdata> list);

	/**
	 * 更新move数据
	 * 
	 * @param list
	 * @return
	 */
	@PostMapping("/order/import/update/Invoice")
	public CommonResult<String> InvoniceForPoUpdate(@RequestBody List<OpsPurchaseinvoice> list);

	/**
	 * 退货生成在途收货数据
	 * 
	 * @param list
	 * @return
	 */
	@PostMapping("/order/handReturnOrderConfirm")
	CommonResult<String> handReturnOrderConfirm(List<CreInvMoveForReturnOrderDto> list);

}
