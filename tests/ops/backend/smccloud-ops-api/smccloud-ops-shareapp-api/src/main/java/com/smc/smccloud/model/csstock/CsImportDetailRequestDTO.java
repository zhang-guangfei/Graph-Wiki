package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * 入库明细清单查询
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:50
 */
@Data
public class CsImportDetailRequestDTO extends BaseQuery {

    private String agentNo;
    private String barcode; // 条码
    private String caseNo; // 箱号
    private String deliveryNo; // 签收单号
    private Integer impType;
    private String invoiceNo; // 发票号
    private String modelNo;
    private String orderNo;
    private String warehouseCode;
    private Integer status;
    private Date beginDate;
    private Date endDate;
    private Integer dateType;
    private String monthDate;
    /**
     * 用户的查询权限(客户、部门、行业、用户)
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;




}
