package com.sales.ops.dto.zd;

import com.sales.ops.dto.purchase.PurchaseInfoForCancel;
import com.sales.ops.dto.purchase.RequestCancelDto;

import java.io.Serializable;

/**
 * 转定页面展示关联关系
 */
public class ZDPageShowOrderBindInvDto implements Serializable {

    private static final long serialVersionUID = 8795461628354951077L;

    //库存id
    private Long invId;
    //库存表 N 在库 T 在途
    private String invTableType;

    //页面展示库存类别
    private String pageShowInvTableType;

    private String invoiceno;

    private String associateNo;

    private Integer associateNoItem;

    private Integer associateNoSplitno;

    private String supplierId;
    //转定数量
    private Integer qty;
    // 是否是二次处理状态或无关联关系状态
    private Boolean exceptionOrNot;
    //是否是拆分型号
    private Boolean assModleFlag;
    // doitemInventory_Id或pcoitemInventory_Id
    private Long itemInvId;
    //是否是调拨单
    private Boolean dbOrNot;

    private String doId;
    // doid
    private String dbDoId;
    // 订单号
    private String orderId;
    //订单项号
    private String orderItem;
    //拆分号
    private Integer num;

    private Integer assNum;
    // pcoId
    private String pcoId;
    //pcoItem
    private Integer pcoItem;

    //可操作标识 可转定标识
    private Boolean actionFlag;

    //不可转定原因
    private String actionMsg;

    //选定库存id
    private Long toInvId;
    private Boolean toInvRiskFlag = false;
    //选定库存类型 N 在库 T 在途
    private String toInvTableType;

    //转定型号
    private String modelNo;

    //库存属性
    private String inventoryTypeCode;

    //当前占用库存仓库
    private String warehouseCode;

    //转定仓库
    private String toWareHouseCode;

    private Integer itemInvSize;


    //是否删除采购标志
    private Boolean delPoFlag;
    //采购取消报文
    private PurchaseInfoForCancel poInfo;

    //操作人
    private String userName;

    private StringBuffer resultLog;

    // 拆分新的doid
    private String assNewJYDoId;

    //创建 新的调拨id
    private String NewDBDoId;

    //拆分新的拆分序号
    private Integer assNewNum;

    public ZDPageShowOrderBindInvDto() {
    }

    public ZDPageShowOrderBindInvDto(Long invId, String invTableType, Integer qty, Boolean exceptionOrNot, Boolean assModleFlag, Long itemInvId,
                                     Boolean dbOrNot, String dbDoId, String orderId, String orderItem,
                                     Integer num, String doId, String pcoId, Integer pcoItem, String modelNo, String inventoryTypeCode, String warehouseCode, String pageShowInvTableType, Boolean actionFlag, String actionMsg){
        this.invId = invId;
        this.invTableType = invTableType;
        this.qty = qty;
        this.exceptionOrNot = exceptionOrNot;
        this.assModleFlag = assModleFlag;
        this.itemInvId = itemInvId;
        this.dbOrNot = dbOrNot;
        this.dbDoId = dbDoId;
        this.orderId = orderId;
        this.orderItem = orderItem;
        this.num = num;
        this.doId = doId;
        this.pcoId = pcoId;
        this.pcoItem = pcoItem;
        //用于前端显示
        this.modelNo = modelNo;
        this.inventoryTypeCode = inventoryTypeCode;
        this.warehouseCode = warehouseCode;
        this.pageShowInvTableType = pageShowInvTableType;

        this.actionFlag = actionFlag;//不可转定原因
        this.actionMsg = actionMsg;//不可转定原因
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public Long getInvId() {
        return invId;
    }

    public void setInvId(Long invId) {
        this.invId = invId;
    }

    public String getInvTableType() {
        return invTableType;
    }

    public void setInvTableType(String invTableType) {
        this.invTableType = invTableType;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Boolean getExceptionOrNot() {
        return exceptionOrNot;
    }

    public void setExceptionOrNot(Boolean exceptionOrNot) {
        this.exceptionOrNot = exceptionOrNot;
    }

    public Boolean getAssModleFlag() {
        return assModleFlag;
    }

    public void setAssModleFlag(Boolean assModleFlag) {
        this.assModleFlag = assModleFlag;
    }

    public Long getItemInvId() {
        return itemInvId;
    }

    public void setItemInvId(Long itemInvId) {
        this.itemInvId = itemInvId;
    }

    public Boolean getDbOrNot() {
        return dbOrNot;
    }

    public void setDbOrNot(Boolean dbOrNot) {
        this.dbOrNot = dbOrNot;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
    }


    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public Boolean getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(Boolean actionFlag) {
        this.actionFlag = actionFlag;
    }

    public Long getToInvId() {
        return toInvId;
    }

    public void setToInvId(Long toInvId) {
        this.toInvId = toInvId;
    }

    public String getToInvTableType() {
        return toInvTableType;
    }

    public void setToInvTableType(String toInvTableType) {
        this.toInvTableType = toInvTableType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getToWareHouseCode() {
        return toWareHouseCode;
    }

    public void setToWareHouseCode(String toWareHouseCode) {
        this.toWareHouseCode = toWareHouseCode;
    }

    public String getDbDoId() {
        return dbDoId;
    }

    public void setDbDoId(String dbDoId) {
        this.dbDoId = dbDoId;
    }

    public Integer getItemInvSize() {
        return itemInvSize;
    }

    public void setItemInvSize(Integer itemInvSize) {
        this.itemInvSize = itemInvSize;
    }

    public PurchaseInfoForCancel getPoInfo() {
        return poInfo;
    }

    public void setPoInfo(PurchaseInfoForCancel poInfo) {
        this.poInfo = poInfo;
    }

    public Boolean getDelPoFlag() {
        return delPoFlag;
    }

    public void setDelPoFlag(Boolean delPoFlag) {
        this.delPoFlag = delPoFlag;
    }

    public String getActionMsg() {
        return actionMsg;
    }

    public void setActionMsg(String actionMsg) {
        this.actionMsg = actionMsg;
    }

    public StringBuffer getResultLog() {
        return resultLog;
    }

    public void setResultLog(StringBuffer resultLog) {
        this.resultLog = resultLog;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getPageShowInvTableType() {
        return pageShowInvTableType;
    }

    public void setPageShowInvTableType(String pageShowInvTableType) {
        this.pageShowInvTableType = pageShowInvTableType;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo;
    }

    public Integer getAssociateNoItem() {
        return associateNoItem;
    }

    public void setAssociateNoItem(Integer associateNoItem) {
        this.associateNoItem = associateNoItem;
    }

    public Integer getAssociateNoSplitno() {
        return associateNoSplitno;
    }

    public void setAssociateNoSplitno(Integer associateNoSplitno) {
        this.associateNoSplitno = associateNoSplitno;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getAssNewJYDoId() {
        return assNewJYDoId;
    }

    public void setAssNewJYDoId(String assNewJYDoId) {
        this.assNewJYDoId = assNewJYDoId;
    }

    public String getNewDBDoId() {
        return NewDBDoId;
    }

    public void setNewDBDoId(String newDBDoId) {
        NewDBDoId = newDBDoId;
    }

    public Integer getAssNewNum() {
        return assNewNum;
    }

    public void setAssNewNum(Integer assNewNum) {
        this.assNewNum = assNewNum;
    }

    public Boolean getToInvRiskFlag() {
        return toInvRiskFlag;
    }

    public void setToInvRiskFlag(Boolean toInvRiskFlag) {
        this.toInvRiskFlag = toInvRiskFlag;
    }
}
