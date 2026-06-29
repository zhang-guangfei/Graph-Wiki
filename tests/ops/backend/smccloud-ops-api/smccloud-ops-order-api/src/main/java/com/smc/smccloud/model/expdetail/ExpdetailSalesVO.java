package com.smc.smccloud.model.expdetail;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/8/21 15:47
 * @Descripton TODO
 */
@Data
public class ExpdetailSalesVO {

    // 联合主键 do_id+expdetail_id+barcode
    private String id;

    // 主订单号不含项号
    private String orderNo;

    // 完整单号
    private String orderFno;

    // 项号
    private String itemNo;

    // 型号
    private String modelNo;

    // 发货数量
    private Integer quantity;

    // 外箱代码
    private String outBarcode;

    // 内箱代码
    private String innerBarcode;

    // 代理店代码
    private String agentNo;
    private String agentNoName;

    // 代理店代码-客户代码   分销商代码-用户代码   用户代码-最终用户代码
    // 分销商代码
    private String userNo;
    private String userNoName;

    // 用户代码
    private String endUserNo;
    private String endUserNoName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date shipDate;

    // 运单号
    private String expressNo;

    // 承运商
    private String expressCompany;
    private String expressCompanyName;

    // 仓库
    private String warehouseCode;
    private String warehouseCodeName;

    // po号
    private String corderNo;

    // 重量
    private BigDecimal weight;

    // 收货人名称
    private String contactPsn;

    // 收货地址
    private String dlvAddress;
}
