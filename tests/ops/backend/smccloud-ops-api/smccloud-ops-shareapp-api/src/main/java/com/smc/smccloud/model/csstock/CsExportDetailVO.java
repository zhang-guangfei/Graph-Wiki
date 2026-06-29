package com.smc.smccloud.model.csstock;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库明细
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:51
 */
@Data
public class CsExportDetailVO {
    private Integer id;
    /**
     * 代理代码
     */
    private String agentNo;

    // 客户名称
    private String agentName;
    // 客户类型
    private String customerTypeName;
    // 仓库名称
    private String wareHourseName;
    /**
     * 归属库房
     */
    private String warehouseCode;
    /**
     * 入库订单
     */
    private String inOrderNo;
    /**
     * 出库订单
     */
    private String expOrderNo;
    /**
     * 型号
     */
    private String modelNo;
    /**
     * 出库数量
     */
    private Integer expQty;
    /**
     * 出库日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expTime;
    /**
     * 生成时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 出库状态
     * 1正常出库
     * 9取消出库（对应已出库后未发出货时要求取消）
     */
    private Integer status;

    private String updateUser;

    private String createUser;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String userNo;
    private String userName;
    private String remark;
    private String corderNo;
    private BigDecimal price;
    private String priceEnduser;
    private String pplNo;
    private String projectNo;
    private String cproductNo;
    private Date balanceDate;
}
