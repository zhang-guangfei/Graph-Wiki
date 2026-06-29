package com.smc.smccloud.model.ordermodify;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author edp02 @Date 2021/11/24 15:45
 */
@Data
public class ApproveOrderModifyDTO implements Serializable {
    private static final long serialVersionUID = -6791074466389332131L;

    private Integer status;

    private String approveReason;

    private List<Integer> ids;

    private List<OrderModifyVO> approveItems;
}
