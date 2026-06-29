package com.smc.smccloud.model.inventory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InventoryLogRequstDTO extends BaseQuery {

    private Long inventoryId;

    private String orderNo;

    private String modelNo;

    private String invoiceNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtTimeEnd;

    private String crtTimeStartStr;
    private String crtTimeEndStr;

    // 指令类型
    private List<String> voucherType;

    private String warehouseCode;

    private Integer type;

    private String itemNo;

    // 排序规则 默认降序
    private String sortRule = "desc";

    private String property;

    private Boolean checkedHistoryData;

}
