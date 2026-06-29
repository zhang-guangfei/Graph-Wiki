package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author edp02 @Date 2022/6/20 10:41
 */
@Data
public class OrdingOrderVO {

    private String warehouseCode;
    private String orderFno;
    private String orderNo;
    private Integer itemNo;
    private String modelno;
    private Integer quantity;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    private String transType;
    private Integer inQty;
    private String orderType;
    private String supplier;

}
