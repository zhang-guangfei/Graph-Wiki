package com.smc.smccloud.model.expdetail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/20 14:54
 * @Descripton TODO
 */

@Data
public class ExpdetailVO {

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 出库单号
     */
    private String deliveryNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单项号
     */
    private Integer itemNo;

    /**
     * 完整订单号
     */
    private String orderFno;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 发货数量
     */
    private Integer quantity;

    private Integer orderQty;

    /**
     * 货物条码
     */
    private String barcode;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    private String customerType;

    /**
     * 用户代码
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 发货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date shipDate;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 发货仓库 （物流）
     */
    private String warehouseCode;

    private String warehouseCodeName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 状态: 1-写入; 2-写入失败
     */
    private Integer optCode;

    /**
     * 客户订单号
     */
    private String corderNo;

    /**
     * 客户型号
     */
    private String cmodelNo;

    /**
     * 箱号
     */
    private String caseNo;

    /**
     * 重量KG
     */
    private Double weight;

    /**
     * 订单类型
     */
    private Integer orderType;

    private String orderTypeName;

    /**
     * 开票抽取标识，默认为0，抽取完写1
     */
    private String invoiceFlag;

    /**
     * 开票抽取时间，开票抽取完回写
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invoiceTime;

    /**
     * 签收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signTime;

    /**
     * 出库区分
     */
    private String stockCode;

    /**
     * 客户货期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 合同订单号
     */
    private String orOrderNo;

    /**
     * 发货状态
     */
    private Integer signStatus;

    private String signStatusName;

    /**
     * 发货当担
     */
    private String sender;

    /**
     * 交货地点 1-直发客户; 2-直发营业; 3-自提
     */
    private String dlvSite;

    private Long id;

    private String deptNo;
    // 营业所名称
    private String deptName;

    // 签收单号
    private String signOrderNo;

    // 收货地址
    private String dlvAddress;

    // 收货人
    private String contactPsn;

    private String endUser;

    private String endUserName;

    private String hlCode;

    private String hrUnitId;

    private List<Long> idList;
}
