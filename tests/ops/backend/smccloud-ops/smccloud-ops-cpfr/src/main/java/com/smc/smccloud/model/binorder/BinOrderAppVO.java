package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2021/11/10 14:58
 */
@Data
public class BinOrderAppVO implements Serializable {

    private static final long serialVersionUID = -7732681467972326792L;

    private Integer modelCount;

    private Long calcId;

    private Integer status;

    private String approveText;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date approveTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;

    private Long appId;

    private Integer modelQty;

    private String warehouseCode;

    private String approveUser;

    private String appUser;

    private String applyText;

    private Integer stockType;

    private String customerNo;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Long propertyId;

    private BigDecimal eamount;

}
