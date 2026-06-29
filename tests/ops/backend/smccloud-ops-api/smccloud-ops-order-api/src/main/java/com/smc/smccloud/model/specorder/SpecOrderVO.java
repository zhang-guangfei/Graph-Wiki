package com.smc.smccloud.model.specorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpecOrderVO {
    private Integer id;

    private String groupNo; //批次号

    private String customerNo; //客户代码

    private String userNo; //用户代码

    private String orderNo; //订单号

    private String corderNo; //客户订单号

    private Integer orderType; //申请类型

    private Integer orderItem; //项号

    private String modelNo; //产品型号

    private Integer quantity; //数量

    private BigDecimal price; //单价

    private BigDecimal orgPrice; //原币单价

    private String orgCurrency; //原币

    private String productName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dlvDate; //交货期

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date manuDlvDate; //制造指定出荷日

    private String cproductNo; //客户型号

    private String remark; //备注

    private String shipTimeStart; // 创建时间开始
    private String shipTimeEnd; // 创建时间结束

    private Date createTime;

    private String createUser; //创建人

    private String exportType; //出口方式

    private String tradeCompanyId; //订单的SMC交易主体

//    private Integer pageNumber;
//
//    private Integer pageSize;

    private BigDecimal eprice;//E价

    private String dlvType;//出货方式

    private String dlvEntire; //处理方式

    private Integer status;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverName;

    private String receiverCompany;

    private String DeptNo;

    private String exportWarehouse;

    private String complaintNo;

    private String province;

    private String city;

    private String district;

    private String transType;

    private String specMark;

}
