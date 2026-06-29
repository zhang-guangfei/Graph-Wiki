package com.smc.smccloud.model.SlowMovingModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SlowMovingModelVO {

    private Integer id;
    private String modelNo;
    private Integer quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastInDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastOutDate;
    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    private String createUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;
    private String updateUser;
    private Integer status;

    private BigDecimal eprice; // E价格

    private BigDecimal lotPrice; // lot价格

    private String warehouseCode; // 仓库代码

    private String supplier; // 场地

    private Integer designTypeId; //产品类别

    private String productName; // 品名

    private Integer leftQty;
}
