package com.smc.smccloud.model.inventory;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/12/3 13:35
 */
@Data
public class OpsInventoryPropertyRequestDTO extends BaseQuery {

    private String inventoryTypeCode;

    private String customerNo;

    private String ppl;

    private String projectCode;

    private String groupCustomerNo;

    private String salesInfoNo;

    private List<String> modelNos;
    private List<Long> propertyIds;
    private List<Long> inventoryIds;
    private Integer months;
    /**
     * 请求日期类型，1，createDate;2:modifyDate
     */
    private Integer dateType;
    private Date beginDate;
    private Date endDate;

    private String queryDateStr;

    private int inventoryType;

    /**
     * 先行在库数
     */
    private Boolean isPres;
    /**
     * 是否查历史数据。
     */
    private  Boolean isHistoryData=false;

}
