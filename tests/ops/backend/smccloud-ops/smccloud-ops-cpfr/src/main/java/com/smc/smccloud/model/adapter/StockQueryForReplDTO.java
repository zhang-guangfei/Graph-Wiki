package com.smc.smccloud.model.adapter;

import lombok.Data;

import java.util.List;

/**
 * 库存查询
 */
@Data
public class StockQueryForReplDTO {

    /**
     * 专用库存类型代码
     * code	code_name
     * GK-TY	顾客在库通用
     * GK-PJ	顾客在库项目
     * GK-PPL	顾客在库PPL
     * ZL-CP	战略在库(产品)
     * ZL-HY	战略在库(行业)
     * ZL-JT	战略在库(集团)
     * ZL-PJ	战略在库(PJ)
     * QB_NO	营业情报
     */
    private  String inventoryTypeCode;

    private String customerNo;
    private String ppl;
    private  String projectCode;
    private  String groupCustomerNo;
    private  String salesInfoNo;

    /**
     * 一次最多50个型号
     */
    private List<String> modelNos;

    /**
     * 是否查仓库的bindata
     */
    private  Boolean isQryBindata;

    /**
     * 是否查分仓在库
     */
    private  Boolean isQrySubWarehouseStock;

    /**
     * 是否查shikomi
     */
    private  Boolean isQryShikomi;
}
