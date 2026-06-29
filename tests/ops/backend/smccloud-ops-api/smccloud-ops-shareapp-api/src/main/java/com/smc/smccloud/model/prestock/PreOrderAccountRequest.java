package com.smc.smccloud.model.prestock;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author wuweidong
 * @create 2023/12/28 13:10
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PreOrderAccountRequest  extends BaseQuery {

    private List<String> deptNos;
    private String modelNo;
    private String orderNo;
    private String inventoryTypeCode;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private String salesInfoNo;
    private List<String>  warehouseCodes;
    private List<String> inventoryTypeCodes;
    private List<Integer> status;
    /**
     * 担当
     */
    private String charger;
    private Date beginDate;
    private Date endDate;
    private String transNo;

    private String beginDateStr;
    private String endDateStr;

    private String applyNo;

    private Boolean transQtyGtZero;

    private int temp;

    private String accountApplyNo;



}
