package com.smc.smccloud.model.customer;


import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/12/12 13:37
 * @Descripton TODO
 */
@Data
public class BatchAddCustomerWldVO {

    private List<OpsCustomerWldateVO> customerNos;

    private String loginUserNo;



}
