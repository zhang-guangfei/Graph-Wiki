package com.smc.smccloud.model.adapter.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhengkai.blog.csdn.net
 * @description sales_customer_base_bind
 * @date 2022-03-03
 */
@Data
public class SalesCustomerClusterBind implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * customer_base_no
     */
    private String customerBaseNo;

    /**
     * customer_base_name
     */
    private String customerBaseName;

    /**
     * customer_no
     */
    private String customerNo;

    /**
     * customer_name
     */
    private String customerName;
}