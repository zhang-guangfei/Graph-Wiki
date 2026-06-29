package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author edp02 @Date 2021/12/22 17:12
 */
@Data
public class PoInvoiceMasterRequest extends BaseQuery {

    private Integer id;
    private  Long invoiceId;
    private String invoiceNo;
    /**
     * 供应商
     */
    private String supplierCode ;

    /**
     * 状态 1发票入库
     *  2、已成本结算
     *  4、发票入库（不入成本）
     */
    private Integer status;
    /**
     *查询类型
     * 1入库时间
     * 2成本结算日期
     * 3物流签收日期
     * 4发票日期
     */
    private Integer qryDateType;
    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date toDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date costDate;

    private String currencyCode;

    private Integer invoiceType;

    /**
     * 签收仓库代码
     */
    private String receiveWarehouseCode;


}
