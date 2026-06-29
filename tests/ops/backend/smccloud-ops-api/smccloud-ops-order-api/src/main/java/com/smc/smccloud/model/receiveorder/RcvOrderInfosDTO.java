package com.smc.smccloud.model.receiveorder;

import com.smc.smccloud.core.model.adapter.DataAuthoritySearchCondition;
import lombok.Data;

import java.util.List;

@Data
public class RcvOrderInfosDTO {

    /**
     * 订单号+项号
     */
    private List<String> orderNos;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 查询权限
     */
    private DataAuthoritySearchCondition dataAuthoritySearchCondition;

}
