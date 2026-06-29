package com.smc.smccloud.model.customer;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/12/27 8:39
 * @Descripton TODO
 */
@Data
public class CustomerWldDelVO {

    // 客户编码集合
    private List<OpsCustomerWldateVO> customerNoList;

    // 登录人
    private String loginUserNo;



}
