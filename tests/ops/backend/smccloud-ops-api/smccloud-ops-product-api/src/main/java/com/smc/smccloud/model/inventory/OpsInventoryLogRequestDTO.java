package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/8 10:12
 * @description
 */
@Data
public class OpsInventoryLogRequestDTO {
    /**
     * 是否查历史数据。
     */
    private  Boolean isHistoryData;

    private List<Long> inventoryIds;
    private List<String> modelNos;
    private List<Long> propertyIds;
    private List<String> warehouseCode;
    /**
     * 0)出库
     * 1）入库
     */
    private  Integer type;

    /**
     * THRK退货入库收货
     * CGRKGK顾客采购入库收货
     * CGRKBK补库采购入库收货
     * DBRK调拨入库收货-仓库间调拨
     * TZRK调账入库
     * TKRK调库入库
     * ZHRK组换入库
     * PYRK盘盈入库收货
     * QBRK情报占用入库
     * QBQX情报占用取消
     * JYCK客户交易出库单
     * DBCK调拨出库
     * CGDBCK采购调拨出库
     * TZCK调账出库
     * TKCK调库出库
     * ZHCK组换出库
     * QBCK情报占用出库
     * QBQX情报占用撤销
     */
    private List<String> voucherTypes;
    /**
     * 查询几个月数据
     */
    private Integer months;
    private Date beginDate;
    private Date endDate;
    /**
     * 库存类别
     * 1）NULL，所有
     * 2）1：通用在库
     * 3）2：顾客在库，
     * 4）3：战略在库，
     * 5）4：先行在库 顾客在库+战略在库，
     * 5）5：营业情报，
     */
    private Integer inventoryType;


}
