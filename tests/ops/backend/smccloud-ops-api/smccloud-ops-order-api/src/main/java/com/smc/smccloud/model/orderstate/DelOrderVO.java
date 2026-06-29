package com.smc.smccloud.model.orderstate;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/9/8 22:20
 * @Descripton TODO
 */
@Data
public class DelOrderVO {

    private Long id;

    private String orderNo;

    private String itemNo;

    private String rorderNo;

    private Date stateDate;

    private String customerNo;

    private String userNo;

    private String modelNo;

    private Integer quantity;

    private BigDecimal price;

    private String stateCode;

    private String optUserNo;

    private String optUserName;

    private String stateDes;

    private String customerType;

    private String name;

    private String hrUnitId;

    private String hlCode;

    private String status;

}
