package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * 出库明细查询条件
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:51
 */
@Data
public class CsExportDetailRequestDTO extends BaseQuery {
    /**
     * 代理代码
     */
    private  String agentNo;
    /**
     * 库房代码
     */
    private  String warehouseCode;
    /**
     * 型号
     */
    private String modelNo;

    /**
     * 入库订单号
     */
//    private  String inOrderNo;
    /**
     * 出库订单号
     */
   private  String expOrderNo;

   private Integer status;

    private String userNo;

    private Date beginDate;
    private Date endDate;

    private Integer dateType;

    /**
     * 用户的查询权限(客户、部门、行业、用户)
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;

    private String monthDate;

    private String corderNo;
}