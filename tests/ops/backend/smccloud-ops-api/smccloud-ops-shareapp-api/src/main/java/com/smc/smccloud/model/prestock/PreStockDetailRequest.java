package com.smc.smccloud.model.prestock;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-02-06 15:18
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PreStockDetailRequest extends BaseQuery {

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 营业所
     */
    private String deptNo;

    /**
     * 处理型号
     */
    private String modelNo;
    /**
     * 申请型号
     */
    private String applyModelNo;
    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 申请状态
     */
    private List<String> applyStatus;

    /**
     * 申请项状态
     */
    private List<String> detailStatus;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 1-普通申请 2-申请自动周转 3-自动补库
     */
    private String replType;

    /**
     * 库存类型
     */
    private String inventoryType;

    /**
     * 备库仓库
     */
    private String warehouseCode;

    /**
     * 行业
     */
    private String indCode;

    /**
     * ppl
     */
    private String pplNo;

    /**
     * 项目号
     */
    private String projectNo;

    /**
     * 集团号
     */
    private String groupCustomerNo;

    /**
     * shikomi类型
     */
    private String shikomiClass;

    /**
     * 供应商代码
     */
    private String supplierCode;

    /**
     * 调库单号
     */
    private String transferNo;

    /**
     * 采购单号
     */
    private String orderNo;

    /**
     * 时间类型:
     * 1-申请时间; 2-确认时间; 3-货期
     */
    private String dateType;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 截止时间
     */
    private Date endTime;
    /**
     * 批量查询类型
     */
    private Integer lotQueryType;
    /**
     * 批量查询数据
     */
    private List<String> lotQueryData;

    private String prepareOrders;
}
