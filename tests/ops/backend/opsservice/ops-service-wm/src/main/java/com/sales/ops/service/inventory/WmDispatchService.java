package com.sales.ops.service.inventory;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.flux.RoSignConfirmDto;
import com.sales.ops.dto.inventory.*;
import com.sales.ops.dto.order.CancelForOrderDto;
import com.sales.ops.dto.order.InventoryForAdjustInputDto;
import com.sales.ops.dto.order.InventoryForTransInputDto;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.purchase.PurchaseUpdateInfo;
import com.sales.ops.enums.WMPurchaseTagEnum;

import java.util.List;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/3 9:48
 * @description：调度算法
 */
public interface WmDispatchService {

    OpsRequestpurchase initOpsRequestpurchase(boolean splitNoSend, InventoryCkByOrderInputDto inputDto,
                                              WMPurchaseTagEnum wmPurchaseTagEnum, int splititemno, String modelno, int qty, String requestwarehouseid, Long bomId) throws OpsException;

    /**
     * @description：客户订单完成物流调度功能
	 * @description：订单分配：整单号调度
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 9:49
	 */
	void OrderdispatchForOrder(Rcvmaster rcvmaster, List<Rcvdetail> rcvdetails) throws OpsException;

	/**
	 * @description：订单分配：单项调度
	 * @author ：c02483
	 * @date ：Created in 2021/10/26 18:58
	 */
	List<OpsOrderAssignResult> OrderdispatchForOrderItem(Rcvmaster rcvmaster, Rcvdetail rcvdetail,
			Boolean orderInitFlag) throws OpsException;


	void updatePurchaseOrder(PurchaseUpdateInfo purchaseUpdateInfo) throws OpsException;

	/**
	 * @description：预到货发票导入
	 * @author ：c02483
	 * @date ：Created in 2021/11/24 14:23
	 */
	void invoiceForPoInit(List<OpsPurchaseinvoice> list) throws OpsException;

	void invoiceForPoUpdate(List<OpsPurchaseinvoice> OpsPurchaseinvoices) throws OpsException;

	/**
	 * 发票导入
	 * 
	 * @description：分纳到货的时候+出库方式（整单集约单仓出库、单项集约整仓出库、随到随发） 当做特发的时候，影响销售单、加工单出库
	 */
	void invoiceForPoConfirm(List<OpsImpdata> list) throws OpsException;

	/**
	 * @description：发票签收
	 * @description：采购如果不允许异仓合并，一张发票只能属于一个仓
	 * @author ：c02483
	 * @date ：Created in 2022/1/14 13:54
	 */
	void invoiceForSignWms(RoSignConfirmDto param) throws OpsException;

	/**
	 * @description：到货确认
	 * @author ：c02483
	 * @date ：Created in 2021/10/3 15:17
	 */
	GoodsConfirmDto goodsConfirm(RoSignConfirmDto param) throws OpsException;

	/**
	 * @description 调拨出库发票数据回传
	 */
	//@Deprecated
	//void handconfirm(HandConfirm handConfirm) throws OpsException;

	/**
	 * 退货单转生成在途数据
	 *
	 * @param list
	 * @return
	 */
	@Deprecated
	void handReturnOrderConfirm(List<CreInvMoveForReturnOrderDto> list) throws OpsException;


	/**
	 * @description：调账单 optType:"+"调整+库存,"-'调账-库存
	 * @author ：c02483
	 * @date ：Created in 2021/10/15 13:33
	 */
	void InventoryForAdjust(InventoryForAdjustInputDto dto) throws OpsException;

	/**
	 * @description：WMS调账单 optType:"+"调整+库存,"-'调账-库存
	 * @author ：c02483
	 * @date ：Created in 2021/10/15 13:33
	 */
	void InventoryForWMSAdjust(InventoryForAdjustInputDto dto) throws OpsException;

	/**
	 * @description：调库单
	 * @author ：c02483
	 * @date ：Created in 2021/10/15 13:32
	 */
	void inventoryForTrans(InventoryForTransInputDto inputDto) throws OpsException;

	/**
	 * 创建在途调库单
	 * @param dto
	 * @param fromMove
	 * @throws OpsException
	 */
	void inventoryMoveForTransOrder(TransOrderDtoForMove dto, OpsInventoryMove fromMove) throws OpsException;

	// 创建组换单生成do指令
	void InventoryForProducChange(InventoryForProducChangeDto dto, Boolean onlyWms) throws OpsException;

	/**
	 * 组换失败回传
	 *
	 * @param orderId
	 * @throws OpsException
	 */
	void wmsProducChangeStatus(String orderId, String msg, long cancelId) throws OpsException;

	/**
	 * @description：情报预约
	 * @author ：c02483
	 * @date ：Created in 2021/12/30 10:16
	 */
	void InventoryForSalesInfoNo(InventoryForAdjustDto dto) throws OpsException;

	/**
	 * @description：情报预约自动取消
	 * @author ：c02483
	 * @date ：Created in 2021/12/30 10:16
	 */
	Integer undoInventoryForSalesInfo(InventoryForAdjustDto dto) throws OpsException;

	Boolean orderChangeToInitDelOrder(CancelForOrderDto inputDto) throws OpsException;

	/**
	 * 调库取消
	 * 
	 * @param inputDto
	 * @return
	 * @throws OpsException
	 */
	Boolean cancelTKCKByOrder(CancelForOrderDto inputDto) throws OpsException;

	/**
	 * 取消订单
	 */
	Boolean cancellationOfOrder(CancelForOrderDto inputDto) throws OpsException;

	/**
	 * 委托在库取消
	 */
	Boolean cancellationOfOrderWT(CancelForOrderDto inputDto) throws OpsException;

	/**
	 * 日志接口（历史用于调试时收集请求临时用） optType 必填 requestParam 必填
	 *
	 * @return ID自增
	 */
	Long addImpInvoiceEventLog(ImpInvoiceEventLog log);

	/**
	 * 纳期客户清单
	 *
	 * @return
	 * @throws OpsException
	 */
	List<String> listDlvCustomers() throws OpsException;

	/**
	 * 取消调拨回传 按发票号
	 *
	 * @param invoiceNo
	 * @throws OpsException
	 */
	void cancelHandconfirmByInvoiceNo(String invoiceNo) throws OpsException;

	/**
	 * 处理中间表RO调整数据 处理RO多收的数量调整
	 * 
	 * @throws OpsException
	 */
	void handOpsRoQtyAdjust(Integer flag) throws OpsException;

}