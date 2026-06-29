package com.sales.ops.utils;

import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoItem;
import com.sales.ops.db.entity.OpsRoItemInventory;
import com.sales.ops.dto.inventory.OpsRoDto;
import com.sales.ops.enums.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 调拨入库单的生成器
 *
 * @author C12961
 * @date 2023/5/24 17:25
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RoBuilder {

    // 类型
    private RoTypeEnum roType;
    private RoSourceEnum roSource;
    private RoStatusEnum roStatus;

    // 单号
    private String roId;
    private String orderId;
    private Integer orderItem;
    private Integer num;
    private Integer assNum;
    private Integer extNum;
    private String invoiceNo;
    private Long invoiceId;
    // 收货信息
    private String modelno;
    private String WarehouseCode;
    // 收货数量
    private Integer qty = 0;
    private Integer recQty = 0;


    List<InvInfo> invs = new ArrayList<>();

    class InvInfo {
        private Long inventoryId;
        private int qty;

        public InvInfo(Long inventoryId, int qty) {
            this.inventoryId = inventoryId;
            this.qty = qty;
        }
    }


    private String supplierId;
    private String customerNo;
    private String userNo;

    // 物流信息
    private String transType;// 运输方式
    private String carried;// 承运商
    private String expressCode;// 运单号

    // 关联信息
    private Long fromInventoryId;
    private InventoryTableTypeEnum inventoryTableType;

    // 产品信息
    private boolean greenCode;
    private QAStatusEnum qaStatus;
    private BigDecimal netWeight;

    // 备注
    private String creator;
    private String remark;


    public static RoBuilder createTKRK(String orderId, Integer orderItem) {
        RoBuilder roBuilder = new RoBuilder();
        roBuilder.roType = RoTypeEnum.TKRK;
        roBuilder.roSource = RoSourceEnum.ALL;
        roBuilder.orderId = orderId;
        roBuilder.orderItem = orderItem;
        roBuilder.num = 0;
        roBuilder.assNum = 0;
        roBuilder.extNum = 0;
        roBuilder.roId = WmOrderNoFactory.TKRK(orderId, orderItem, 0, 0);
        return roBuilder;
    }

    // 必填
    public RoBuilder warehouseInfo(String warehouseCode) {
        this.WarehouseCode = warehouseCode;
        return this;
    }

    // invInfo和addInvInfo至少填一项
    public RoBuilder invInfo(String modelno, Integer qty) {
        this.modelno = modelno;
        this.qty = qty;
        return this;
    }


    public RoBuilder addInvInfo(OpsInventoryMove move, Integer qty) {
        this.modelno = move.getModelno();
        InvInfo invInfo = new InvInfo(move.getInventoryId(), qty);
        invs.add(invInfo);
        return this;
    }


    public RoBuilder orderFromInv(Long invId, InventoryTableTypeEnum table) {
        this.fromInventoryId = invId;
        this.inventoryTableType = table;
        return this;
    }

    public RoBuilder customerInfo(String customerNo, String supplierId, String userNo) {
        this.customerNo = customerNo;
        this.supplierId = supplierId;
        this.userNo = userNo;
        return this;
    }

    public RoBuilder customerInfo(String customerNo) {
        this.customerNo = customerNo;
        return this;
    }


    public RoBuilder orderProduct(boolean greenCode, QAStatusEnum qaStatus, BigDecimal netWeight) {
        this.greenCode = greenCode;
        this.qaStatus = qaStatus;
        this.netWeight = netWeight;
        return this;
    }

    public RoBuilder orderProduct(String greenCode) {
        this.greenCode = greenCode != null && StringUtils.equals(greenCode, "0");
        this.qaStatus = QAStatusEnum.NORMAL;
        this.netWeight = null;
        return this;
    }


    public RoBuilder orderCreator(String creator, String remark) {
        this.creator = creator;
        this.remark = remark;
        return this;
    }


    public OpsRoDto build() throws OpsException {
        OpsRo opsRo = buildOpsRo();
        List<OpsRoItemInventory> roItemInventoryList = null;
        if (this.invs.size() > 0) {
            roItemInventoryList = buildOpsRoItemInventoryList();
        }
        OpsRoItem roItem = buildOpsRoItem();
        return new OpsRoDto(opsRo, roItem, roItemInventoryList);
    }

    private List<OpsRoItemInventory> buildOpsRoItemInventoryList() {
        List<OpsRoItemInventory> roItemInventoryList = new ArrayList<>();
        Date date = new Date();
        for (InvInfo inv : invs) {
            OpsRoItemInventory itemInventory = new OpsRoItemInventory();
            itemInventory.setRoId(this.roId);
            itemInventory.setRoItem(1);
            itemInventory.setInventoryId(inv.inventoryId);
            itemInventory.setQty(inv.qty);
            itemInventory.setRecQty(0);
            itemInventory.setDelflag(0);
            itemInventory.setVersion(0L);
            itemInventory.setCreTime(date);
            itemInventory.setCreator(creator);
            itemInventory.setModifyTime(date);
            itemInventory.setModifier(creator);
            roItemInventoryList.add(itemInventory);
        }
        return roItemInventoryList;
    }


    private OpsRo buildOpsRo() throws OpsException {
        Date date = new Date();
        OpsRo opsRo = new OpsRo();
        // 类型
        if (this.roType != null) {
            opsRo.setRoType(this.roType.getType());
        } else {
            throw Exceptions.OpsException("创建ro失败，请输入ro类型");
        }
        if (this.roSource != null) {
            opsRo.setRoSource(this.roSource.getType());
        } else {
            opsRo.setRoSource(RoSourceEnum.EMPTY.getType());
        }
        // 状态
        if (this.roStatus != null) {
            opsRo.setRoStatus(this.roStatus.getStatus());
        } else {
            opsRo.setRoStatus(RoStatusEnum.INIT.getStatus());
        }
        opsRo.setIsWms(RoStatusEnum.INIT.getStatus());
        opsRo.setIsSign(false);
        // 单号、批次
        opsRo.setRoId(roId);
        if (this.orderId != null) {
            opsRo.setOrderId(this.orderId);
        } else {
            throw Exceptions.OpsException("创建失败，请输入orderId");
        }
        if (this.orderItem != null) {
            opsRo.setOrderItem(this.orderItem.toString());
        } else {
            throw Exceptions.OpsException("创建失败，请输入orderIitem");
        }
        if (this.num != null) {
            opsRo.setNum(this.num);
        } else {
            opsRo.setNum(0);
        }
        if (this.assNum != null) {
            opsRo.setAssNum(this.assNum);
        } else {
            opsRo.setAssNum(0);
        }
        opsRo.setExtNum(0);
        opsRo.setInvoiceNo(this.invoiceNo);
        opsRo.setInvoiceId(this.invoiceId);
        // 客户信息
        if (this.WarehouseCode != null) {
            opsRo.setWarehouseCode(this.WarehouseCode);
        } else {
            throw Exceptions.OpsException("创建失败，请输入WarehouseCode");
        }
        opsRo.setSupplierId(this.supplierId);
        opsRo.setCustomerNo(this.customerNo);
        opsRo.setUserNo(this.userNo);
        // 运输信息
        opsRo.setTransType(this.transType);
        opsRo.setCarried(this.carried);
        opsRo.setExpressCode(this.expressCode);
        // 创建信息
        opsRo.setRemark(this.remark);
        opsRo.setDelflag(0);
        opsRo.setCreTime(date);
        opsRo.setCreator(creator);
        opsRo.setModifyTime(date);
        opsRo.setModifier(creator);
        opsRo.setVersion(0);
        return opsRo;
    }

    private OpsRoItem buildOpsRoItem() throws OpsException {
        OpsRoItem roItem = new OpsRoItem();
        // 单号
        roItem.setRoId(this.roId);
        roItem.setRoItem(1);
        // 数量
        if (this.qty != null && this.qty > 0) {
            roItem.setQty(this.qty);
        } else if (this.invs.size() > 0) {
            Integer sumQty = invs.stream().map(i -> i.qty).reduce(Integer::sum).get();
            roItem.setQty(sumQty);
        } else {
            throw Exceptions.OpsException("创建ro失败，请输入qty");
        }
        roItem.setRecQty(this.recQty);
        // 产品信息
        if (this.modelno != null) {
            roItem.setModelno(this.modelno);
        } else {
            throw Exceptions.OpsException("创建ro失败，请输入modelno");
        }
        roItem.setQaStatus(qaStatus != null ? qaStatus.getType() : null);
        roItem.setGreenCode(greenCode ? "H" : null);
        roItem.setNetweight(netWeight);
        // 关联关系
        if (this.fromInventoryId != null) {
            if (this.inventoryTableType != null) {
                roItem.setFromInventoryId(this.fromInventoryId);
                roItem.setFromInventoryTableType(inventoryTableType.getType());
            } else {
                throw Exceptions.OpsException("创建ro失败，要输入fromInventoryId,inventoryTableType不能为空");
            }
        }
        // 创建信息
        roItem.setVersion(0);
        roItem.setDelflag(0);
        Date date = new Date();
        roItem.setCreTime(date);
        roItem.setCreator(creator);
        roItem.setRemark(this.remark);
        roItem.setModifyTime(date);
        roItem.setModifier(creator);
        return roItem;
    }


}
