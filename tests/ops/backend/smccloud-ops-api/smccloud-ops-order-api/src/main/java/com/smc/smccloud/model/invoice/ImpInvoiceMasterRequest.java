package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2021/12/1 11:04
 */
@Data
public class ImpInvoiceMasterRequest extends BaseQuery {

    private List<Long> ids;
    private Long id;

    private String invoiceNo;
    /**
     * 供应商
     */
    private String supplierCode ;

    /**
     * 状态 1预计到货
     *  3、已入库
     *  9、已删除
     */
    private Integer status;
    /**
     *查询类型
     * 1发货时间
     * 2预计到货时间
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

    private Date sendTimeStart;
    private Date sendTimeEnd;

    private Date prearriveDateStart;
    private Date prearriveDateEnd;

    private Date updateTimeStart;
    private Date updateTimeEnd;

    private Date invoiceDateStart;
    private Date invoiceDateEnd;
    private Integer invoiceType;
}
