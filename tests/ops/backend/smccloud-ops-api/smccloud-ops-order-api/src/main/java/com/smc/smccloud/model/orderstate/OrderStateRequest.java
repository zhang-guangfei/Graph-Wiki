package com.smc.smccloud.model.orderstate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import com.smc.smccloud.core.model.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class OrderStateRequest {


    private String orderNo; // 订单号

    private List<String> orderNos;

    private String modelNo; // 型号

    private List<Integer> stateCode; // 状态代码 (stateCode)

    private List<String> supplierCodes; // 供应商代码

    private List<String> orderTypes; // 订单类型

    private List<String> purchaseTypes; // 采购类型

    private String deptNo; // 营业所代码
    private String customerNo; // 客户代码

    private String corderNo;
    private String cmodelNo;
    private String userNo; // 用户代码

    private String supplierNo; // 供应商编号

    // 仓库代码
    private List<String> warehouseCodes;


    // 客户交货期
    private String custDlvDateStart;
    private String custDlvDateEnd;

    // 指定工厂出荷日
    private String poDlvDateStart;
    private String poDlvDateEnd;

    // 指定物流发送日
    private String shipDateStart;
    private String shipDateEnd;

    // 工厂纳期
    private String poReplyDateStart;
    private String poReplyDateEnd;

    // 供应商发出日
    private String poShipDateStart;
    private String poShipDateEnd;

    // 实际出厂日
    private String poFacExpdateStart;
    private String poFacExpdateEnd;

    // 预计到达日期
    private String esArrivalDateStart;
    private String esArrivalDateEnd;

    // 更新时间
    private String updateTimeStart;
    private String updateTimeEnd;

    // 订单接单日期
    private String orderDateStart;
    private String orderDateEnd;

    // 供应商接单器
    private String supplierRcvTimeStart;
    private String supplierRcvTimeEnd;

    private  String dlvUpdTimeStart;
    private  String dlvUpdTimeEnd;

    private Page page;


    // -------门户追加所需字段

    /**
     * 下单日期 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 下单日期 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 用户的查询权限(客户、部门、行业、用户)
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;

    private List<String> status; // 状态代码

    /**
     * 需要查询的部门条件
     */
    private List<String> deptCode;

    // 手配号 supplierOrders
    private List<String> supplierOrderNos;

    /**
     * 特殊查询类型 1-返信超期（过了返信纳期）
     */
    private  Integer specQryType;

}
