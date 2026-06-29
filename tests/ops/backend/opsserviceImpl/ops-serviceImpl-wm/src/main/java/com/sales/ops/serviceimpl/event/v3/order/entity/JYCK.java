package com.sales.ops.serviceimpl.event.v3.order.entity;

import com.sales.ops.db.entity.OpsDo;
import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.enums.DoSourceEnum;
import com.sales.ops.enums.DoWaitTypeEnum;
import com.sales.ops.enums.InventoryTableTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class JYCK {
    // 交易出库的doId
    private String jyDoId;
    // 十三位单号
    private String orderId;
    private Integer orderItem;
    private Integer num;
    private Integer assNum;
    // 拆分类型
    private DoSourceEnum doSource;
    // 集约仓库
    private String warehouseCode;
    private String modelno;
    private Integer qty;
    // 等待类型（不准，辅助作用）
    private DoWaitTypeEnum waitType;

    // 下发标识和状态
    private boolean isWms; // 0未下发，1下发
    private Integer wmsStatus;// 0下发，1组波次，2拣货，5待揽收
    private Date wmsTime;// 下发时间
    private Date waveTime;// 组波次时间
    private Date packTime;// 集箱时间
    // 指定物流发货期
    private Date wlDate;
    // tms回传的预计送达日
    private Date tmsExpectedDeliveryDate;

    private Date wmsExpectedDeliveryDate;




    // 关联关系
    private List<Relation> relations = new ArrayList<>();
    // 数据来源
    private OpsDo jyckDo;
    private OpsDoItem jyckItem;
    private OpsPcoItem pcoItem;

    public JYCK(OpsDo jyck, OpsDoItem jyckItem) {
        this.jyDoId = jyck.getDoId();
        this.orderId = jyck.getOrderId();
        this.orderItem = Integer.valueOf(jyck.getOrderItem());
        this.num = jyck.getNum();
        this.assNum = jyck.getAssNum();
        this.doSource = DoSourceEnum.parse(jyck.getDoSource());
        this.warehouseCode = jyck.getGatherWarehouseCode();
        this.modelno = jyckItem.getModelno();
        this.qty = jyckItem.getQty();
        this.waitType = DoWaitTypeEnum.parse(jyck.getWaitType());
        this.isWms = Objects.equals(jyck.getIsWms(), 1);
        this.wlDate = jyck.getWlDate();
        this.wmsStatus = jyck.getWmsStatus();
        this.wmsTime = jyck.getWmsTime();
        this.waveTime = jyck.getWaveTime();
        this.packTime = null;
        this.jyckDo = jyck;
        this.jyckItem = jyckItem;
        this.tmsExpectedDeliveryDate = jyck.getTmsExpectedDeliveryDate();
        this.wmsExpectedDeliveryDate = jyck.getWmsExpectedDeliveryDate();
    }


    public JYCK(OpsDo jyck, OpsPcoItem pcoItem) {
        this.jyDoId = jyck.getDoId();
        this.orderId = jyck.getOrderId();
        this.orderItem = Integer.valueOf(jyck.getOrderItem());
        this.num = jyck.getNum();
        this.assNum = pcoItem.getPcoItem();
        this.doSource = DoSourceEnum.parse(jyck.getDoSource());
        this.warehouseCode = jyck.getGatherWarehouseCode();
        this.modelno = pcoItem.getSubModelno();
        this.qty = pcoItem.getSubQty();
        this.waitType = DoWaitTypeEnum.parse(pcoItem.getWaitType());
        this.isWms = Objects.equals(jyck.getIsWms(), 1);
        this.wlDate = jyck.getWlDate();
        this.wmsStatus = jyck.getWmsStatus();
        this.wmsTime = jyck.getWmsTime();
        this.waveTime = jyck.getWaveTime();
        this.packTime = null;
        this.jyckDo = jyck;
        this.pcoItem = pcoItem;
        this.tmsExpectedDeliveryDate = jyck.getTmsExpectedDeliveryDate();
        this.wmsExpectedDeliveryDate = jyck.getWmsExpectedDeliveryDate();

    }

    // 新增货齐关联关系
    public void addRelation(Relation relation) {
        this.relations.add(relation);
    }

    // 获取就绪的关联关系
    private List<OKRelation> getOKRelations() {
        return this.relations.stream().filter(OKRelation.class::isInstance).map(OKRelation.class::cast).collect(Collectors.toList());
    }

    public int getDBT3Qty(String dbDoId) {
        return this.relations.stream()
                .filter(DBRelation.class::isInstance)
                .map(DBRelation.class::cast)
                .filter(db-> InventoryTableTypeEnum.TRANS.equals(db.getInvTable()))
                .filter(db-> dbDoId.equals(db.getDbDo().getDoId()))
                .map(Relation::getUseQty).reduce(Integer::sum).orElse(0);
    }

    // 已分配数量（do/pcoItemInventory的数量和）
    public int getAllotQty() {
        return this.relations.stream().map(Relation::getUseQty).reduce(Integer::sum).orElse(0);
    }

    // 就绪数量（do/pcoItemInventory的N库存数量和）
    public int getReadyQty() {
        return getOKRelations().stream().map(Relation::getUseQty).reduce(Integer::sum).orElse(0);
    }

    // 交易出库已发数量
    public int getOutQty() {
        return getOKRelations().stream().map(Relation::getOutQty).reduce(Integer::sum).orElse(0);
    }

    // 是否分配完成
    public boolean isFullAllot() {
        return getAllotQty() >= qty;
    }

    // 是否就绪
    public boolean isFullReady() {
        return getReadyQty() >= this.qty;
    }

    public boolean isStart() {
        return wmsStatus != null && wmsStatus > 0;
    }
    public boolean isOperation() {
        return wmsStatus != null && wmsStatus > 1;
    }

}
