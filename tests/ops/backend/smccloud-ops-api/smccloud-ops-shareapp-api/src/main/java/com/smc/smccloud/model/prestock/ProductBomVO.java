package com.smc.smccloud.model.prestock;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/7 16:03
 * @Descripton TODO
 */
@Data
public class ProductBomVO {

    private static final long serialVersionUID = 1L;

    /**
     * 型号
     */
    private String ModelNo;

    /**
     * 优先整型号采购
     */
    private Boolean priorityComplete;

    /**
     * 优先级别，越小级别越高，默认1
     */
    private Integer priorityLevel;

    /**
     * 是否下发wms  0：初始,1：已下发，2：失败
     */
    private Integer isWms;

    /**
     * 是否有效
     */
    private Boolean IsValid;

    private String UpdateUser;

    private Long bomId;

    private String CreateUser;

    private String UpdateTime;

    private String CreateTime;

}
