package com.sales.ops.service.wmOrder;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.inventory.CreDoByInventoryDto;
import com.sales.ops.dto.inventory.WmDoConfirmDto;
import com.sales.ops.dto.order.OrderInventoryQueryDto;
import com.sales.ops.dto.order.UpdateForOrderDto;
import com.sales.ops.dto.replacement.*;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.dto.zd.ZDPageShowOrderBindInvDto;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoTypeEnum;
import com.sales.ops.enums.PurchaseCancelSourceEnum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/11/6 15:24
 */
public interface DoPcoLogicCenterService {

    Boolean checkDlvEntireAssShipment(String orderNo);

    //一、出库确认回传
    String doConfirm(WmDoConfirmDto param) throws OpsException;

    // 二、更新物流交货期和客户期望交货期
    Map<String, String> updateDoPcoWlday(String orderId, String orderItem, Date dlvDateParam, Boolean updateRcvFlag, UserDto userDto)throws OpsException;

    // 更新物流交货期和客户期望交货期 子方法
    Map<String, String> modifyDeliveryDay(String orderId, String orderItem, Date dlvDateParam, Boolean updateRcvFlag, UserDto userDto) throws OpsException;

    // 三、订单修改
    Map<String, String> modifyDoPcoForOrder(UpdateForOrderDto dto) throws OpsException;

    // 根据关联关系拆分do
    void createAssDo(OpsDo oldOpsDo, int qty, UserDto userDto, DoTypeEnum doTypeEnum, String dlvEntire) throws OpsException;

    // 四、转定 获取现状关联关系
    void showItemInvZD(String orderId, String orderItem, Integer num, Integer assNum, Boolean assModelFlag, List<ZDPageShowOrderBindInvDto> outPutZD) throws OpsException ;

    //五、转定 绑定新的关联关系
    void handleOrderBindInvZD(ZDPageShowOrderBindInvDto param) throws OpsException;

    //六、根据数量拆分do
    CreDoByInventoryDto createAssQtyDo(String formatDoId, Integer num, Integer assNum, OpsDo oldOpsDo,
                                       OpsDoItem oldOpsDoItem, int qty, UserDto userDto,
                                       DoTypeEnum doTypeEnum, String waitType, DoSourceEnum doSourceEnum) throws OpsException;

    //七、删采购后调用删相关指令
    List<OrderInventoryQueryDto> delPoSoDelDoNew(String orderNo, Integer itemNo, Integer splitItemNo,
                                                 List<OpsRequestpurchase> requestpurchaseList,
                                                 PurchaseCancelSourceEnum cancelSourceEnum) throws OpsException;
    //八、采购异仓收货
    void cgDiffWarehouseModifyDO(OpsDo cgDo, String pcoId, Integer pcoItem) throws OpsException;

    //1.写日志表
    void writeLog(String orderId, String orderItem, String result, String params);

    //九、通知发货 1.页面查询列表
    PageInfo<NotifyShipmentPlanPageDto> findNotifyShipmentPlanPage(PageModel<NotifyShipmentPlanPageDto> pageModel);

    List<NotifyShipmentPlanPageDto>findNotifyShipmentPlanListExport(NotifyShipmentPlanExportPageDto param);

    List<NotifyShipmentPlanPageDto>findNotifyShipment(NotifyShipmentFindParam param);

    List<NotifyShipmentPlanPageDto> findNotifyShipment(String wmOrderId);

    List<NotifyShipmentPlanDto> findNotifyShipmentPlanList(String orderId, String orderItem);

    void releaseNotify(String orderId, String orderItem, String msg);

    //九、通知发货 2.页面查询子表
    List<NotifyShipmentPlanItemPageDto> findNotifyShipmentPlanItemItem(String planNo);

    //九、通知发货 3.计算物流指定货期
    Date getNotifyShipmentWlDate(Date hopeDate, String orderId, String orderItem) throws OpsException;

    NotifyShipmentVerifyDto getNotifyShipmentVerifyForPage(String orderFno) throws OpsException;

    //九、通知发货 4.验证通知发货数量
    NotifyShipmentVerifyDto getNotifyShipmentVerifyDto(NotifyShipmentPlanDto orderFno) throws OpsException;

    //九、通知发货-验证数据
    void checkNotifyShipment(NotifyShipmentPlanDto param) throws OpsException;

    //九、通知发货 5.页面保存主表
    void createNotifyShipmentPlan(NotifyShipmentPlanDto param) throws OpsException;

    void getPlanCountOrderAndBingPlan(NotifyShipmentCondition condition) throws OpsException;

    //九、通知发货 6.获取可执行计划
    NotifyShipmentVerifyAssDto getNotifyShipmentPlan(NotifyShipmentCondition condition) throws OpsException;

    /**
     * 外部调用拆分指令方法
     * 拆分类型
     * 1.通知发货
     * 2.随发分配
     * 3.外部到货 CGDBCK TKCK
     */
    void splitOrderByDlvEntry(NotifyShipmentCondition condition)throws OpsException;

    //九、通知发货 7.订单匹配计划 外部调用 **********************************************************
    void notifyShipmentPlanMatchOrder(NotifyShipmentCondition condition) throws OpsException;

    //九、通知发货 8.doItenInv排序
    List<OpsDoItemInventory> sortDoItemInvListV1(List<OpsDoItemInventory> list);

    //九、通知发货 9.pcoItenInv排序
    List<OpsPcoItemInventory>  sortPcoItemInvListV1(List<OpsPcoItemInventory> list);

    //十、通知发货收集拆分调拨单List 后调用splitDBOrder拆分
    void notifyShipmentSplitDBOrder(String orderId, String orderItem)  throws OpsException;

    //十、通知发货拆分调拨单
    void splitDBOrder(OpsDo opsDo) throws OpsException;
}
