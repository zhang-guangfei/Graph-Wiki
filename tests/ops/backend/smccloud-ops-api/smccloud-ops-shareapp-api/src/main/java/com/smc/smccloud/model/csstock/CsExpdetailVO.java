package com.smc.smccloud.model.csstock;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/12 17:09
 * @Descripton TODO
 */


@Data
public class CsExpdetailVO {

    private static final long serialVersionUID = 1L;

    /**
     * 出库订单号
     */
    @NotEmpty
    private String expOrderNo;

    /**
     * 型号
     */
    @NotEmpty
    private String modelNo;

    /**
     * 出库数量
     */
    @NotNull
    private Integer expQty;

    /**
     * 出库时间
     */
    private Date expTime;

    /**
     * 入库订单号
     */
    private String inOrderNo;

    /**
     * 状态 1-待出库  2-已出库  4-出库取消
     */
    private Integer status;

    private Date updateTime;

    private String corderNo;

    private String pplNo;

    private String priceEnduser;

    private String updateUser;

    private String remark;

    private Integer id;

    private String projectNo;

    @NotEmpty
    private String warehouseCode;

    private BigDecimal price;


    private Date createTime;

    @NotEmpty
    private String agentNo;

    private String userNo;

    private String createUser;

    private String cproductNo;

    private Long inventoryId;
    private String inventoryTypeCode;
    private String doId;
    private Integer itemType;
    private String salesModelNo;


}
